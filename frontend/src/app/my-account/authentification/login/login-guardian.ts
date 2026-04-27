import { Injectable } from "@angular/core";
import { CanActivate, Router } from "@angular/router";
import { isLoggedIn } from "../../../app.config";

@Injectable()
export class LoginGuardian implements CanActivate {

    constructor(private router: Router) { }

    /**
     * Método que se ejecuta antes de activar la ruta.
     * Verifica si el usuario está autenticado.
     * Si no lo está, redirige a la página de inicio de sesión.
     * @param route Ruta activada
     * @param state Estado del router
     * @returns true si el usuario está autenticado, false en caso contrario
     */
    canActivate() {

        if (!isLoggedIn()) {
            this.router.navigate(['/login']);
            return false;
        }

        return true;
    }

}