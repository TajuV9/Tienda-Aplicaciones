import { Component } from '@angular/core';
import { AccordionModule } from 'primeng/accordion';
import { ButtonModule } from 'primeng/button';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-tos',
    standalone: true,
    imports: [CommonModule, AccordionModule, ButtonModule],
    templateUrl: './tos.component.html',
    styleUrls: ['./tos.component.css']
})
export class TosComponent {
    active: number = 0;

    /**
     * Sección de términos y condiciones de la tienda de aplicaciones.
     * Contiene las secciones con sus respectivos encabezados y contenidos.
     */
    sections = [
        {
            value: 0,
            header: '1. Aceptación de los Términos',
            content: 'Al utilizar esta tienda de aplicaciones, acepta cumplir estos Términos y Condiciones. Si no está de acuerdo, no use nuestros servicios.'
        },
        {
            value: 1,
            header: '2. Contenido permitido',
            content: 'Solo se permite subir contenido original, incluyendo aplicaciones, juegos, música, videos o cualquier otro archivo.'
        },
        {
            value: 2,
            header: '3. Propiedad intelectual',
            content: 'Los creadores conservan los derechos de autor sobre su contenido. Al subir contenido, otorgas a la plataforma una licencia limitada.'
        },
        {
            value: 3,
            header: '4. Moderación',
            content: 'Nos reservamos el derecho de revisar, aprobar, rechazar o eliminar cualquier contenido que no cumpla con nuestras políticas.'
        },
        {
            value: 4,
            header: '5. Seguridad y cuentas',
            content: 'Es responsabilidad del usuario mantener segura su cuenta. No comparta sus credenciales y notifíquenos ante cualquier actividad sospechosa.'
        },
        {
            value: 5,
            header: '6. Cambios en los términos',
            content: 'Podemos modificar estos términos en cualquier momento. El uso continuado del servicio implicará su aceptación.'
        },
        {
            value: 6,
            header: '7. Contacto',
            content: 'Si tiene preguntas o dudas, contáctenos a través del centro de soporte.'
        }
    ];
}
