import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { ToastLimiterService } from '../../services/toast-limiter.service';
import { InputTextModule } from 'primeng/inputtext';
import { FloatLabel } from 'primeng/floatlabel';
import { Select } from 'primeng/select';
import { UpdateZoneService } from './update-zone.service';
import { ActivatedRoute } from '@angular/router';

import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { FileSelectEvent } from 'primeng/fileupload';
import Swal from 'sweetalert2';



@Component({
    selector: 'app-update-zone',
    imports: [CommonModule, FormsModule, InputTextModule, FloatLabel, Select],
    providers: [ToastLimiterService],
    templateUrl: './update-zone.component.html',
    styleUrl: './update-zone.component.css'
})
export class UpdateZoneComponent {
    @Input() appId!: number;

    notification: string = '';

    title: string = '';
    description: string = '';
    selectedCategory: any;
    category: any[] = [];

    isEditMode = true;
    initialAppId!: number;
    initialName = '';
    initialDescription = '';
    descriptionCharCount: number = 0;
    descriptionCharLimit: number = 1000;
    initialCategory = '';

    uploadedImage: File | null = null;
    uploadedDocument: File | null = null;

    imagePreview: string | null = null;
    hasLoadedImage: boolean = false;
    hasLoadedFile: boolean = false;

    fileUrl: string | null = null;
    fileName: string | null = null;

    // Imágenes de previsualización
    previewImages: { file?: File, url: string, fromDb?: boolean, id?: number }[] = [];
    maxPreviewImages: number = 10;

    ngOnInit() {
        window.scrollTo({ top: 0, behavior: 'smooth' });

        this.route.queryParams.subscribe(params => {
            this.isEditMode = params['isEditMode'] === 'true';
            this.category = [
                { name: 'Aplicación', code: 'APPLICATION' },
                { name: 'Música', code: 'MUSIC' },
                { name: 'Vídeo', code: 'VIDEO' },
                { name: 'Libro', code: 'BOOK' }
            ];

            if (this.appId) {
                this.loadData(this.appId);
            }
        });

        this.updateDescriptionCount();
    }

    constructor(
        private updateZoneService: UpdateZoneService,
        private route: ActivatedRoute,
        private toastLimiter: ToastLimiterService
    ) { }

    /**
     * Carga los datos de la aplicación por ID y asigna los valores a las variables del componente.
     * También carga la imagen y el archivo asociados a la aplicación.
     * @param id - ID de la aplicación a cargar.
     */
    loadData(id: number): void {
        this.updateZoneService.getAppInfoById(id).subscribe(data => {
            this.title = data.title;
            this.description = data.description;
            this.selectedCategory = this.category.find(cat => cat.code === data.category);
            this.updateDescriptionCount();

            this.initialName = data.title;
            this.initialDescription = data.description;
            this.initialCategory = data.category;

            // Filtrar media para obtener solo las imágenes tipo IMAGE
            const imageMedias = Array.isArray(data.media)
                ? data.media.filter((m: any) => m.type === 'IMAGE')
                : [];
            const imageIds = imageMedias.map((m: any) => m.id);

            if (imageIds.length > 0) {
                this.loadPreviewImagesFromIds(imageIds.slice(0, this.maxPreviewImages));
            } else {
                this.previewImages = [];
            }
        });

        // Imagen
        this.updateZoneService.getAppImageById(id).subscribe(
            imageBlob => {
                if (imageBlob && imageBlob.size > 0) {
                    const reader = new FileReader();
                    reader.onload = () => {
                        this.imagePreview = reader.result as string;
                        this.hasLoadedImage = true;
                        this.uploadedImage = null;
                    };
                    reader.readAsDataURL(imageBlob);
                } else {
                    this.hasLoadedImage = false;
                    this.imagePreview = null;
                }
            },
            error => {
                this.hasLoadedImage = false;
                this.imagePreview = null;
            }
        );

        // Archivo
        this.updateZoneService.getAppFileById(id).subscribe(
            response => {
                const fileBlob = response.body;
                const contentDisposition = response.headers.get('Content-Disposition');
                let fileName = 'archivo';
                if (contentDisposition) {
                    const match = contentDisposition.match(/filename="?([^"]+)"?/);
                    if (match) {
                        fileName = decodeURIComponent(match[1]);
                    }
                }
                if (fileBlob && fileBlob.size > 0) {
                    this.fileUrl = URL.createObjectURL(fileBlob);
                    this.fileName = fileName;
                    this.hasLoadedFile = true;
                    this.uploadedDocument = null;
                } else {
                    this.hasLoadedFile = false;
                    this.fileUrl = null;
                    this.fileName = null;
                }
            },
            error => {
                this.hasLoadedFile = false;
                this.fileUrl = null;
                this.fileName = null;
            }
        );
    }

    /**
     * Carga todas las imágenes de previsualización a partir de un array de ids.
     * @param ids Array de ids de imágenes de previsualización.
     */
    loadPreviewImagesFromIds(ids: number[]): void {
        this.previewImages = [];
        if (!ids || ids.length === 0) return;
        const requests = ids.map(id =>
            this.updateZoneService.getPreviewImageById(id).pipe(
                catchError(() => of(null))
            )
        );
        forkJoin(requests).subscribe((blobs: (Blob | null)[]) => {
            blobs.forEach((blob, idx) => {
                if (blob && blob.size > 0) {
                    const reader = new FileReader();
                    reader.onload = () => {
                        this.previewImages.push({
                            url: reader.result as string,
                            fromDb: true,
                            id: ids[idx]
                        });
                    };
                    reader.readAsDataURL(blob);
                } else {
                    this.toastLimiter.error('Error al cargar imagen de previsualización ❌', `No se pudo cargar la imagen con ID ${ids[idx]}.`, 4000);
                }
            });
        });
    }

    /**
     * Maneja el evento de entrada de imagen, actualizando la imagen cargada y la vista previa.
     * @param event - Evento de entrada de archivo.
     */
    onImageInput(event: Event) {
        const input = event.target as HTMLInputElement;
        if (input.files && input.files.length > 0) {
            this.uploadedImage = input.files[0];
            const reader = new FileReader();
            reader.onload = () => {
                this.imagePreview = reader.result as string;
                this.hasLoadedImage = false; // Ahora es una nueva imagen
            };
            reader.readAsDataURL(this.uploadedImage);
        }
    }

    /**
     * Maneja el evento de entrada de archivo, actualizando el archivo cargado.
     * @param event - Evento de entrada de archivo.
     */
    onFileInput(event: Event) {
        const input = event.target as HTMLInputElement;
        if (input.files && input.files.length > 0) {
            this.uploadedDocument = input.files[0];
            this.hasLoadedFile = false; // Ahora es un nuevo archivo
        }
    }

    /**
     * Permite el arrastre de archivos sobre el área de carga.
     * @param event - Evento de arrastre.
     */
    allowDrop(event: DragEvent) {
        event.preventDefault();
    }

    /**
     * Maneja el evento de soltar un archivo o imagen en el área de carga.
     * @param event - Evento de arrastre y soltar.
     * @param type - Tipo de archivo ('image' o 'file').
     */
    handleDrop(event: DragEvent, type: 'image' | 'file' | 'preview') {
        event.preventDefault();
        if (event.dataTransfer?.files?.length) {
            const file = event.dataTransfer.files[0];
            if (type === 'image' && file.type.startsWith('image/')) {
                this.uploadedImage = file;
                const reader = new FileReader();
                reader.onload = () => this.imagePreview = reader.result as string;
                reader.readAsDataURL(file);
            } else if (type === 'file') {
                this.uploadedDocument = file;
            } else if (type === 'preview' && file.type.startsWith('image/')) {
                if (this.previewImages.length < this.maxPreviewImages) {
                    const reader = new FileReader();
                    reader.onload = () => {
                        this.previewImages.push({ file, url: reader.result as string });
                    };
                    reader.readAsDataURL(file);
                } else {
                    this.toastLimiter.error('Límite de imágenes', `Solo puedes subir hasta ${this.maxPreviewImages} imágenes de previsualización.`, 4000);
                }
            }
        }
    }

    onPreviewImagesInput(event: Event) {
        const input = event.target as HTMLInputElement;
        if (input.files && input.files.length > 0) {
            const files = Array.from(input.files);
            const availableSlots = this.maxPreviewImages - this.previewImages.length;
            const filesToAdd = files.slice(0, availableSlots);

            for (const file of filesToAdd) {
                if (!file.type.startsWith('image/')) continue;
                const reader = new FileReader();
                reader.onload = () => {
                    this.previewImages.push({ file, url: reader.result as string, fromDb: false });
                };
                reader.readAsDataURL(file);
            }
            if (files.length > availableSlots) {
                this.toastLimiter.error('Límite de imágenes', `Solo puedes subir hasta ${this.maxPreviewImages} imágenes de previsualización.`, 4000);
            }
        }
    }

    removePreviewImage(index: number, number: number) {
        Swal.fire({
            title: '¿Eliminar imagen?',
            text: 'Esta acción no se puede deshacer.',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Sí, eliminar',
            cancelButtonText: 'Cancelar',
            confirmButtonColor: '#d33',
            cancelButtonColor: '#3085d6'
        }).then(result => {
            if (result.isConfirmed) {
                this.updateZoneService.deleteContentMediaById(index).subscribe({
                    next: () => {
                        this.toastLimiter.success('Imagen eliminada', 'Se eliminó correctamente', 5000);
                        this.previewImages.splice(number, 1);

                    },
                    error: err => {
                        const msg = err?.error?.error || 'No se pudo eliminar el medio';
                        this.toastLimiter.error('Error', msg, 5000);
                    }
                });
            }
        });

    }

    onImageSelect(event: FileSelectEvent) {
        // Siempre reemplaza la imagen anterior por la nueva seleccionada
        if (event.files.length > 0) {
            this.uploadedImage = event.files[0];
            this.toastLimiter.info('Imagen seleccionada 🖼️', this.uploadedImage.name, 3000);
        } else {
            this.uploadedImage = null;
        }
    }

    /**
     * Maneja la selección de un archivo desde el selector de archivos.
     * Siempre reemplaza el archivo anterior por el nuevo seleccionado.
     * @param event - Evento de selección de archivo.
     */
    onFileSelect(event: FileSelectEvent) {
        // Siempre reemplaza el archivo anterior por el nuevo seleccionado
        if (event.files.length > 0) {
            this.uploadedDocument = event.files[0];
            this.toastLimiter.info('Archivo seleccionado 📄', this.uploadedDocument.name, 3000);
        } else {
            this.uploadedDocument = null;
        }
    }

    /**
     * Publica el archivo con los datos ingresados en el formulario.
     * Comprueba si hay cambios antes de enviar la actualización.
     * Valida los datos antes de proceder con la publicación.
     * @param form - Formulario que contiene los datos a publicar.
     */
    publishFile(form: NgForm): void {
        // Comprobar si el contenido no ha cambiado
        const hasNewPreviewImages = this.previewImages.some(img => !img.fromDb && img.file instanceof File);


        const noChange =
            this.title === this.initialName &&
            this.description === this.initialDescription &&
            this.selectedCategory?.code === this.initialCategory &&
            !this.uploadedImage &&
            !this.uploadedDocument &&
            !hasNewPreviewImages;

        if (noChange) {
            this.toastLimiter.warn('Sin cambios ⚠️', 'No se puede actualizar porque el contenido no ha cambiado.', 4000);
            return;
        }

        if (!this.validateData(this.title, this.description, this.selectedCategory, this.uploadedImage, this.uploadedDocument)) {
            this.toastLimiter.error('Error de validación 🚨', this.notification, 5000);
            return;
        }

        const requests = [];

        // Actualizar descripción, título y categoría
        requests.push(
            this.updateZoneService.updateAppInfoById(
                this.appId,
                this.title,
                this.description,
                this.selectedCategory?.code
            ).pipe(
                catchError(() => {
                    this.toastLimiter.error('Error al actualizar descripción ❌', 'No se pudo actualizar la descripción.', 4000);
                    return of(null);
                })
            )
        );

        // Actualizar imagen si es nueva
        if (this.uploadedImage instanceof File) {
            requests.push(
                this.updateZoneService.updateAppImageById(this.appId, this.uploadedImage).pipe(
                    catchError(() => {
                        this.toastLimiter.error('Error al subir imagen ❌', 'No se pudo actualizar la imagen.', 4000);
                        return of(null);
                    })
                )
            );
        }

        // Actualizar archivo si es nuevo
        if (this.uploadedDocument instanceof File) {
            requests.push(
                this.updateZoneService.updateAppFileById(this.appId, this.uploadedDocument).pipe(
                    catchError(() => {
                        this.toastLimiter.error('Error al subir archivo ❌', 'No se pudo actualizar el archivo.', 4000);
                        return of(null);
                    })
                )
            );
        }

        const newPreviewImages = this.previewImages.filter(img => !img.fromDb && img.file instanceof File);
        for (const img of newPreviewImages) {
            // img.file está garantizado
            requests.push(
                this.updateZoneService.uploadPreviewImage(this.appId, img.file!).pipe(
                    catchError(() => {
                        this.toastLimiter.error('Error al subir imagen de previsualización ❌', img.file!.name, 4000);
                        return of(null);
                    })
                )
            );
        }


        forkJoin(requests).subscribe(() => {
            this.toastLimiter.success('Actualización exitosa ✅', `Se actualizó "${this.title}" correctamente.`, 4000);
            // Recarga los datos originales para que la comprobación de cambios funcione correctamente en el siguiente intento
            this.loadData(this.appId);
        });
    }

    /**
     * Valida los datos ingresados en el formulario antes de publicar.
     * Comprueba que todos los campos obligatorios estén completos y que no excedan los límites establecidos.
     * @param title - Título del contenido.
     * @param selectedCategoryCode - Código de la categoría seleccionada.
     * @param description - Descripción del contenido.
     * @param uploadedImage - Imagen cargada (opcional).
     * @param uploadedDocument - Documento cargado (opcional).
     * @returns true si los datos son válidos, false en caso contrario.
     */
    validateData(
        title: string,
        selectedCategoryCode: string,
        description: string,
        uploadedImage: File | null,
        uploadedDocument: File | null
    ): boolean {
        let validated = true;
        let notification: string = 'Errores en la publicación:';

        const allFieldsFilled = title || selectedCategoryCode || description || uploadedImage || uploadedDocument || this.hasLoadedImage || this.hasLoadedFile;

        if (!allFieldsFilled) {
            notification = 'Por favor, completa todos los campos obligatorios.';
            validated = false;
        } else {
            if (!this.title || !this.title.trim()) {
                notification += '\n -Completa el campo de Título.';
                validated = false;
            } else if (this.title.length < 3) {
                notification += '\n -El campo Título debe tener al menos 3 caracteres.';
                validated = false;
            }

            if (!this.selectedCategory) {
                notification += '\n -Selecciona una categoría.';
                validated = false;
            }

            if (!this.description || this.isEmptyHtml(this.description)) {
                notification += '\n -Completa el campo de Descripción.';
                validated = false;
            } else if (this.descriptionCharCount > this.descriptionCharLimit) {
                notification += `\n -El campo de Descripción no puede superar los ${this.descriptionCharLimit} caracteres.`;
                validated = false;
            }

            // Solo muestra error si no hay ni imagen subida ni cargada
            if (!this.uploadedImage && !this.hasLoadedImage) {
                notification += '\n -No has seleccionado ningún Icono.';
                validated = false;
            }

            // Solo muestra error si no hay ni archivo subido ni cargado
            if (!this.uploadedDocument && !this.hasLoadedFile) {
                notification += '\n -No has seleccionado ningún Archivo.';
                validated = false;
            }
        }

        this.notification = notification;
        return validated;
    }

    /**
     * Verifica si el HTML está vacío o solo contiene espacios.
     * @param html - Cadena de texto HTML a verificar.
     * @returns true si el HTML está vacío o solo contiene espacios, false en caso contrario.
     */
    isEmptyHtml(html: string): boolean {
        const div = document.createElement('div');
        div.innerHTML = html;

        const text = div.textContent || div.innerText || '';
        return text.trim().length === 0;
    }

    /**
     * Actualiza el contador de caracteres de la descripción.
     * Solo cuenta los caracteres del texto plano, ignorando etiquetas HTML.
     */
    updateDescriptionCount(): void {
        this.descriptionCharCount = (this.description || '').trim().length;
    }

    /**
     * Resetea el formulario y limpia los campos de imagen y archivo.
     * Mantiene la previsualización de la imagen si ya estaba cargada desde el backend.
     * @param form - Formulario a resetear.
     */
    resetForm(form: any): void {
        form.resetForm();
        this.uploadedImage = null;
        this.uploadedDocument = null;
        this.previewImages = [];
        // Mantén la previsualización si ya estaba cargada desde backend
        if (!this.hasLoadedImage) {
            this.imagePreview = null;
        }
    }
}
