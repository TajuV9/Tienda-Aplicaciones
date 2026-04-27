import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { ToastLimiterService } from '../../services/toast-limiter.service';
import { InputTextModule } from 'primeng/inputtext';
import { FloatLabel } from 'primeng/floatlabel';
import { Select } from 'primeng/select';
import { FileSelectEvent } from 'primeng/fileupload';

import { MyFilesService } from './my-files.service';

interface Category {
    name: string;
    code: string;
}

@Component({
    selector: 'app-my-files',
    standalone: true,
    imports: [CommonModule, FormsModule, InputTextModule, FloatLabel, Select],
    providers: [ToastLimiterService],
    templateUrl: './my-files.component.html',
    styleUrl: './my-files.component.css'
})
export class MyFilesComponent implements OnInit {
    @Input() initialName: string = '';
    @Input() initialDescription: string = '';
    @Input() initialCategory: string = '';
    @Input() initialAppId: string = '';
    @Input() isEditMode: boolean = false;
    @Output() published = new EventEmitter<void>();

    notification: string = '';

    title: string = '';
    category: Category[] = [];
    selectedCategory: Category | undefined;
    description: string = '';
    descriptionCharCount: number = 0;
    descriptionCharLimit: number = 1000;
    editMode: boolean = false;

    uploadedImage: File | null = null;
    uploadedDocument: File | null = null;

    imagePreview: string | null = null;

    // Para previsualizaciones
    previewImages: { file: File, url: string }[] = [];
    maxPreviewImages: number = 10;

    ngOnInit() {
        window.scrollTo({ top: 0, behavior: 'smooth' });

        this.category = [
            { name: 'Aplicación', code: 'APPLICATION' },
            { name: 'Música', code: 'MUSIC' },
            { name: 'Vídeo', code: 'VIDEO' },
            { name: 'Libro', code: 'BOOK' }
        ];

        this.updateDescriptionCount();
    }

    constructor(
        private toastLimiter: ToastLimiterService,
        private myFilesService: MyFilesService
    ) { }

    /**
     * Maneja el evento de entrada de imagen.
     * Lee el archivo de imagen seleccionado y lo convierte a una URL de datos para previsualización.
     * @param event 
     */
    onImageInput(event: Event) {
        const input = event.target as HTMLInputElement;
        if (input.files && input.files.length > 0) {
            this.uploadedImage = input.files[0];
            const reader = new FileReader();
            reader.onload = () => {
                this.imagePreview = reader.result as string;
            };
            reader.readAsDataURL(this.uploadedImage);
        }
    }

    /**
     * Maneja el evento de entrada de archivo.
     * Almacena el archivo seleccionado para su posterior publicación.
     * @param event 
     */
    onFileInput(event: Event) {
        const input = event.target as HTMLInputElement;
        if (input.files && input.files.length > 0) {
            this.uploadedDocument = input.files[0];
        }
    }

    /**
     * Permite el arrastre de archivos sobre el área de carga.
     * @param event 
     */
    allowDrop(event: DragEvent) {
        event.preventDefault();
    }

    /**
     * Maneja el evento de soltar archivos en el área de carga.
     * Si se suelta una imagen, la previsualiza; si es un archivo, lo almacena para su publicación.
     * @param event 
     * @param type Tipo de archivo ('image' o 'file')
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

    /**
     * Maneja la selección de una imagen.
     * @param category La categoría seleccionada.
     */
    onImageSelect(event: FileSelectEvent) {
        if (event.files.length > 0) {
            this.uploadedImage = event.files[0];
            this.toastLimiter.info('Imagen seleccionada 🖼️', this.uploadedImage.name, 3000);
        } else {
            this.uploadedImage = null;
        }
    }

    /**
     * Maneja la selección de un archivo.
     * Almacena el archivo seleccionado para su posterior publicación.
     * @param event 
     */
    onFileSelect(event: FileSelectEvent) {
        if (event.files.length > 0) {
            this.uploadedDocument = event.files[0];
            this.toastLimiter.info('Archivo seleccionado 📄', this.uploadedDocument.name, 3000);
        } else {
            this.uploadedDocument = null;
        }
    }

    /**
     * Maneja la selección de imágenes de previsualización.
     * Permite hasta 10 imágenes, muestra miniaturas y permite eliminarlas.
     */
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
                    this.previewImages.push({ file, url: reader.result as string });
                };
                reader.readAsDataURL(file);
            }
            if (files.length > availableSlots) {
                this.toastLimiter.error('Límite de imágenes', `Solo puedes subir hasta ${this.maxPreviewImages} imágenes de previsualización.`, 4000);
            }
        }
    }

    /**
     * Elimina una imagen de previsualización por índice.
     */
    removePreviewImage(index: number) {
        this.previewImages.splice(index, 1);
    }

    /**
     * Publica el archivo con los datos proporcionados.
     * Valida los datos antes de enviar la solicitud de publicación.
     * Si la publicación es exitosa, sube el icono y el archivo asociado.
     * Además, si hay, sube las imágenes de previsualización.
     * @param form Formulario de Angular para resetear después de la publicación.
     */
    publishFile(form?: NgForm) {

        if (!this.validateData(this.title, this.selectedCategory?.code || '', this.description, this.uploadedImage, this.uploadedDocument)) {
            this.toastLimiter.error('Error de validación 🚨', this.notification, 5000);
            return;
        }

        const categoryCode = this.selectedCategory ? this.selectedCategory.code : '';

        this.myFilesService.publish(
            this.title.trim(),
            this.description.trim(),
            categoryCode
        ).subscribe({
            next: async (response) => {
                if (response.status >= 200 && response.status < 300 && response.body && response.body.id) {
                    const contentId = response.body.id;

                    try {
                        await this.myFilesService.uploadIcon(contentId, this.uploadedImage!);
                        await this.myFilesService.uploadFile(contentId, this.uploadedDocument!);

                        if (this.previewImages.length > 0) {
                            await this.myFilesService.uploadPreviewImages(contentId, this.previewImages.map(img => img.file));
                        }

                        this.toastLimiter.success('🎉 Publicación exitosa', 'Tu archivo ha sido publicado correctamente. 👍', 5000);
                        if (form) {
                            form.resetForm();
                        }
                        this.resetForm();
                        this.published.emit();
                    } catch (err: any) {
                        this.toastLimiter.error('Error al subir archivo o icono', err?.error?.message || 'Error al subir el archivo o icono.', 5000);
                    }
                } else {
                    this.toastLimiter.error('Error', response.body?.message || 'Error al publicar el archivo', 5000);
                }
            },
            error: (errorResponse) => {
                const backendMsg = errorResponse.error?.message || errorResponse.error?.error;
                const errors = errorResponse.error?.errors;
                if (errors && Array.isArray(errors)) {
                    for (const err of errors) {
                        this.toastLimiter.error('Error', err, 5000);
                    }
                } else if (backendMsg) {
                    this.toastLimiter.error('Error', backendMsg, 5000);
                } else {
                    this.toastLimiter.error('Error', 'Error desconocido al publicar el archivo', 5000);
                }
            }
        });
    }

    /**
     * Valida los datos ingresados por el usuario antes de publicar.
     * Verifica que todos los campos obligatorios estén completos y que no excedan los límites establecidos.
     * @param title Título del archivo.
     * @param selectedCategoryCode Código de la categoría seleccionada.
     * @param description Descripción del archivo.
     * @param uploadedImage Imagen subida.
     * @param uploadedDocument Documento subido.
     * @returns Verdadero si los datos son válidos, falso en caso contrario.
     */
    validateData(title: string, selectedCategoryCode: string, description: string, uploadedImage: File | null, uploadedDocument: File | null): boolean {
        let validated = true;
        let notification: string = 'Errores en la publicación:';

        const allFieldsFilled = title || selectedCategoryCode || description || uploadedImage || uploadedDocument;

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

            if (!this.uploadedImage) {
                notification += '\n -No has seleccionado ningún Icono.';
                validated = false;
            }

            if (!this.uploadedDocument) {
                notification += '\n -No has seleccionado ningún Archivo.';
                validated = false;
            }
        }

        this.notification = notification;
        return validated;
    }

    /**
     * Verifica si el HTML proporcionado está vacío.
     * @param html El HTML a verificar.
     * @returns Verdadero si el HTML está vacío, falso en caso contrario.
     */
    isEmptyHtml(html: string): boolean {
        const div = document.createElement('div');
        div.innerHTML = html;

        const text = div.textContent || div.innerText || '';
        return text.trim().length === 0;
    }

    /**
     * Actualiza el contador de caracteres de la descripción.
     * Se llama cada vez que se modifica la descripción para reflejar el número actual de caracteres.
     */
    updateDescriptionCount(): void {
        this.descriptionCharCount = (this.description || '').trim().length;
    }

    /**
     * Resetea el formulario y los campos del componente.
     * Limpia los campos de título, categoría, descripción, imagen y documento.
     * Si se proporciona un formulario, lo resetea también.
     * @param form Formulario de Angular para resetear.
     */
    resetForm(form?: NgForm): void {
        this.title = '';
        this.selectedCategory = undefined;
        this.description = '';
        this.uploadedImage = null;
        this.uploadedDocument = null;
        this.previewImages = [];
        if (form) {
            form.resetForm();
        }
    }

    /**
     * Establece los datos iniciales del componente.
     * Se utiliza para cargar datos al editar un archivo existente.
     * @param name Nombre del archivo.
     * @param description Descripción del archivo.
     * @param category Categoría del archivo.
     * @param appId ID de la aplicación asociada (si aplica).
     * @param imageUrl URL de la imagen del archivo (opcional).
     */
    setInitialData(name: string, description: string, category: string, appId: string, imageUrl: string | null) {
        this.title = name;
        this.description = description;
        this.initialCategory = category;
        this.initialAppId = appId;

        this.selectedCategory = this.category.find(cat => cat.code === category) || undefined;

        if (imageUrl) {
            this.imagePreview = imageUrl;
        } else {
            this.imagePreview = null;
        }

        this.updateDescriptionCount();
    }

}
