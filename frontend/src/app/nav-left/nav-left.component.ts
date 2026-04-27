import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
    selector: 'app-nav-left',
    imports: [RouterLink, RouterLinkActive],
    templateUrl: './nav-left.component.html',
    styleUrl: './nav-left.component.css'
})
export class NavLeftComponent {
}
