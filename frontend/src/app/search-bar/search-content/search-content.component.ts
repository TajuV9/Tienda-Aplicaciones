import { CommonModule, Location } from '@angular/common';
import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { ItemComponent } from '../../explorer/item/item.component';
import { SearchContentService, AppItem } from './search-content.service';
import { SearchBarComponent } from "../search-bar.component";

@Component({
  selector: 'app-item-section',
  imports: [CommonModule, ItemComponent, SearchBarComponent, RouterLink],
  providers: [Location],
  templateUrl: './search-content.component.html',
  styleUrl: './search-content.component.css'
})
export class SearchContentComponent {
  @Input() currentSection!: string;
  @Input() type!: string;
  @Input() searchContent!: string;

  applications: AppItem[] = [];


  constructor(private route: ActivatedRoute, private location: Location, private SearchContent: SearchContentService) {
    this.route.params.subscribe(params => {
      this.searchContent = params['searchContent'];
    });
  }

  /**
   * Recupera los parámetros de la URL y llama al servicio para obtener los datos de la API.
   */
  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.currentSection = params['currentSection'];
      this.type = params['type'];
      switch (this.currentSection) {
        case "home":
          this.currentSection = "todo tipo de archivos";
          break;
        case "games":
          this.currentSection = "juegos";
          break;
        case "music":
          this.currentSection = "musica";
          break;
        case "books":
          this.currentSection = "libros";
          break;
      }
      this.fetchApplications();
    });
  }

  /**
   * Llama al servicio para obtener los datos de la API y los asigna a la variable applications.
   */
  fetchApplications(): void {
    this.SearchContent.getItemsBySearch(this.searchContent, 0,30).subscribe({
      next: (data) => this.applications = data,
      error: (err) => console.error('Error al obtener las aplicaciones:', err)
    });
  }

  /**
   * Navega a la ruta anterior en el historial del navegador.
   */
  goBack() {
    this.location.back();
  }
}