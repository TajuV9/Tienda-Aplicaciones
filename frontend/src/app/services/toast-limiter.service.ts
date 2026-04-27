import { Injectable } from '@angular/core';
import { MessageService } from 'primeng/api';

export interface ToastMessage {
    severity?: 'success' | 'info' | 'warn' | 'error';
    summary?: string;
    detail?: string;
    life?: number;
    key?: string;
}

@Injectable({
    providedIn: 'root'
})
export class ToastLimiterService {
    private maxMessages = 3;
    private queue: ToastMessage[] = [];

    constructor(private messageService: MessageService) { }

    add(message: ToastMessage) {
        //Verificar duplicados
        const isDuplicate = this.queue.some(m =>
            m.summary === message.summary &&
            m.detail === message.detail &&
            m.severity === message.severity
        );

        if (isDuplicate) {
            return;
        }

        //Aplicar el número máximo de mensajes visibles
        if (this.queue.length >= this.maxMessages) {
            this.queue.shift();
        }

        //Agregar a la cola interna
        this.queue.push(message);

        //Mostrar mensajes emergentes
        this.messageService.clear();
        this.queue.forEach(msg => this.messageService.add(msg));

        //Eliminar automáticamente de la cola interna (3000 ms después -> predeterminado)
        const life = message.life ?? 3000;
        setTimeout(() => {
            this.queue = this.queue.filter(m =>
                !(m.summary === message.summary &&
                    m.detail === message.detail &&
                    m.severity === message.severity)
            );
        }, life + 100); //Small buffer to ensure it has gone
    }


    //En caso de que queramos eliminar mensajes específicos
    clear(key?: string) {
        if (key) {
            this.queue = this.queue.filter(msg => msg.key !== key);
        } else {
            this.queue = [];
        }
        this.messageService.clear(key);
    }

    // Notification methods
    success(summary: string, detail: string, life: number = 5000) {
        this.add({ severity: 'success', summary, detail, life });
    }

    error(summary: string, detail: string, life: number = 5000) {
        this.add({ severity: 'error', summary, detail, life });
    }

    warn(summary: string, detail: string, life: number = 5000) {
        this.add({ severity: 'warn', summary, detail, life });
    }

    info(summary: string, detail: string, life: number = 5000) {
        this.add({ severity: 'info', summary, detail, life });
    }
}
