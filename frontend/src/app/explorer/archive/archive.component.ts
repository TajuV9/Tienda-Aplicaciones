import { Component, Input, resource, ViewChild } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { CommonModule, Location } from '@angular/common';
import { ArchiveService } from './archive.service';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import { HttpResponse } from '@angular/common/http';
import { ToastLimiterService } from '../../services/toast-limiter.service';
import Swal from 'sweetalert2';
import { CreateReviewComponent } from './create-review/create-review.component';
import { ShowReviewComponent } from './show-review/show-review.component';
import { filter } from 'rxjs';
import { isLoggedIn } from '../../app.config';
import { CarouselModule } from 'primeng/carousel';


@Component({
    selector: 'app-game-detail',
    templateUrl: './archive.component.html',
    styleUrls: ['./archive.component.css'],
    imports: [CommonModule, CreateReviewComponent, ShowReviewComponent, CarouselModule],
    providers: [Location, ToastLimiterService]
})
export class ArchiveComponent {
    @Input() id!: number;
    @ViewChild(ShowReviewComponent) showReviewComponent!: ShowReviewComponent;

    name: string = '';
    role: string = '';
    isUser: boolean = false;
    userHasReview: boolean = false;
    hasUserReview: boolean = false;
    archive: any = {};
    fromCreator = false;
    sanitizedDescription: SafeHtml = '';
    imageUrls: string[] = [];
    selectedImage: string | null = null;

    mediaId: number = 1;

    constructor(
        private route: ActivatedRoute,
        private location: Location,
        private router: Router,
        private archiveService: ArchiveService,
        private sanitizer: DomSanitizer,
        private toastLimiter: ToastLimiterService
    ) {
        this.route.params.subscribe(params => {
            this.id = params['id'];
        });

        this.router.events.pipe(
            filter(event => event instanceof NavigationEnd)
        ).subscribe(() => {
            if (isLoggedIn()) {
                this.loadProfileData();
            } else {
                this.role = '';
                this.name = '';
                this.isUser = false;
            }
        });
        this.route.params.subscribe(params => {
            const id = +params['id'];
            this.loadArchive(id);
            this.loadReviews(id);

        });

        this.route.queryParams.subscribe(params => {
            this.fromCreator = params['fromCreator'] === 'true';
        });

    }

    /**
     * Carga los datos del perfil del usuario actual.
     * Utiliza el servicio ArchiveService para obtener la información del perfil.
     */
    loadProfileData(): void {
        this.archiveService.getProfile().subscribe({
            next: (profile) => {
                this.name = profile.body?.firstName ?? '';
                this.role = profile.body?.role ?? '';
                this.isUser = this.role === 'USER' || this.role === 'CREATOR';
            }
        });
    }

    /**
     * Carga las reseñas del contenido especificado por contentId.
     * Actualiza la variable userHasReview según si el usuario tiene una reseña o no.
     * @param contentId ID del contenido para el cual se cargan las reseñas.
     */
    loadReviews(contentId: number): void {
        this.archiveService.getReviews(contentId).subscribe({
            next: (res) => {
                this.userHasReview = res.userReview !== null;
            },
            error: err => {
                console.error('Error al obtener reseñas:', err);
                this.userHasReview = false;
            }
        });
    }

    /**
     * Método que se ejecuta al inicializar el componente.
     * Carga los parámetros de la ruta y los parámetros de consulta.
     * Desplaza la ventana al inicio.
     */
    ngOnInit() {
        window.scrollTo({ top: 0, behavior: 'smooth' });

        this.route.params.subscribe(params => {
            const id = +params['id'];
            this.loadArchive(id);
        });

        this.route.queryParams.subscribe(params => {
            this.fromCreator = params['fromCreator'] === 'true';
        });

        if (this.imageUrls.length > 0) {
            this.selectedImage = this.imageUrls[0];
        }

    }

    /**
     * Descarga el archivo asociado al ID especificado.
     * Utiliza el servicio ArchiveService para obtener el archivo y lo descarga.
     */
    download() {
        this.archiveService.getArchiveFile(Number(this.id)).subscribe({
            next: (response: HttpResponse<Blob>) => {
                const blob = response.body!;
                let filename = '';

                const contentDisposition = response.headers.get('content-disposition');
                if (contentDisposition) {
                    const match = contentDisposition.match(/filename="?([^"]+)"?/);
                    if (match && match[1]) {
                        filename = match[1];
                    }
                }

                const url = window.URL.createObjectURL(blob);
                const a = document.createElement('a');
                a.href = url;
                a.download = filename;
                a.target = '_blank';
                document.body.appendChild(a);
                a.click();
                a.remove();
                window.URL.revokeObjectURL(url);
            },
            error: err => {
                console.error('Error al descargar archivo:', err);
                alert('No se pudo descargar el archivo.');
            }
        });
    }

    /**
     * Carga los detalles del archivo del contenido especificado por ID.
     * Utiliza el servicio ArchiveService para obtener los datos del archivo.
     * Sanitiza la descripción para evitar problemas de seguridad al mostrar HTML.
     * @param id ID del archivo a cargar.
     */
    loadArchive(id: number) {
        this.archiveService.getArchiveById(id).subscribe({
            next: data => {
                this.archive = {
                    ...data,
                    stars: Math.round(data.rating),
                };

                this.imageUrls = data.media
                    ?.filter((m: any) => m.type === 'IMAGE')
                    .map((img: any) => img.url) || [];

                this.sanitizedDescription = this.sanitizer.bypassSecurityTrustHtml(this.archive.description || '');
            },
            error: err => {
                console.error('Error al cargar el contenido:', err);
            }
        });
    }

    //Devuelve la cantidad de estrellas completas
    get fullStars() {
        return Array(this.archive.stars || 0).fill(0);
    }

    //Devuelve la cantidad de estrellas vacías
    get emptyStars() {
        return Array(5 - (this.archive.stars || 0)).fill(0);
    }

    /**
     * Navega a la ruta anterior en el historial del navegador.
     * Utiliza el servicio Location para realizar la navegación.
     */
    goBack() {
        this.location.back();
    }

    /**
     * Navega a la sección de creación de contenido con los parámetros necesarios.
     * Utiliza el servicio Router para realizar la navegación.
     */
    updateApp() {
        Swal.fire({
            title: `¿Actualizar "${this.archive.title}"?`,
            text: 'Es necesario resubir el archivo al actualizar',
            icon: 'info',
            showCancelButton: true,
            confirmButtonText: 'Actualizar',
            cancelButtonText: 'Cancelar',
            confirmButtonColor: '#d33',
            cancelButtonColor: '#3085d6'
        }).then(result => {
            if (result.isConfirmed) {
                this.router.navigate(['/creator-zone'], {
                    queryParams: {
                        section: 'update',
                        appId: this.archive.id,
                        isEditMode: true
                    }
                });
            }
        })
    }

    /**
     * Elimina el archivo del contenido actual.
     * Muestra una alerta de confirmación antes de proceder con la eliminación.
     * Utiliza el servicio ArchiveService para realizar la eliminación.
     */
    deleteApp() {
        Swal.fire({
            title: `¿Eliminar "${this.archive.title}"?`,
            text: 'Esta acción no se puede deshacer.',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Sí, eliminar',
            cancelButtonText: 'Cancelar',
            confirmButtonColor: '#d33',
            cancelButtonColor: '#3085d6'
        }).then(result => {
            if (result.isConfirmed) {
                this.archiveService.deleteArchiveById(this.archive.id).subscribe({
                    next: () => {
                        this.toastLimiter.success('Eliminado 🗑️', 'Contenido eliminado correctamente', 5000);

                        this.router.navigate(['/creator-zone'], {
                            queryParams: { section: 'my-apps' }
                        });
                    },
                    error: err => {
                        this.toastLimiter.error('Error', 'No se pudo eliminar el contenido', 5000);
                    }
                });
            }
        })
    }


    /**
     * Método que se ejecuta al inicializar el componente.
     * Carga las reseñas del contenido y verifica si el usuario tiene una reseña.
     */
    onReviewCreated(): void {
        this.showReviewComponent.loadReviews();
        this.hasUserReview = true;
        this.loadArchive(this.id);

    }

    /**
     * Método que se ejecuta cuando se elimina una reseña.
     * Actualiza el estado de las reseñas del usuario.
     */
    onReviewDeleted(): void {
        this.hasUserReview = false;
        this.userHasReview = false;
        this.loadArchive(this.id);
    }

    /**
     * Método que se ejecuta cuando se actualiza una reseña.
     * Vuelve a cargar el archivo para reflejar los cambios en las reseñas.
     */
    onReviewUpdated(): void {
        this.loadArchive(this.id);
    }

    deleteImage(): void {
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
                this.archiveService.deleteContentMediaById(this.mediaId).subscribe({
                    next: () => {
                        this.toastLimiter.success('Imagen eliminada', 'Se eliminó correctamente', 5000);
                        this.loadArchive(this.id);
                    },
                    error: err => {
                        const msg = err?.error?.error || 'No se pudo eliminar el medio';
                        this.toastLimiter.error('Error', msg, 5000);
                    }
                });
            }
        });


    }

}

