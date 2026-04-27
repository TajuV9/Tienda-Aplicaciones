import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CarouselModule } from 'primeng/carousel';
import { AppContent, AppsService } from '../apps/apps.service';
import { HomeSectionComponent } from './home-section/home-section.component';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  imports: [CarouselModule, HomeSectionComponent, CommonModule, RouterLink],

})
export class HomeComponent implements OnInit {
  @Input() currentSection!: string;

  applications: AppContent[] = [];
  music: AppContent[] = [];
  videos: AppContent[] = [];
  books: AppContent[] = [];

  isLoading = true;

  sections = [
    { title: 'Apps', type: 'APPLICATION', route: 'application' },
    { title: 'Música', type: 'MUSIC', route: 'music' },
    { title: 'Videos', type: 'VIDEO', route: 'videos' },
    { title: 'Libros', type: 'BOOK', route: 'books' }
  ];

  images = [
    { currentSection: 'home1' },
    { currentSection: 'home2' },
    { currentSection: 'home3' }
  ];

  constructor(private appsService: AppsService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    window.scrollTo({ top: 0, behavior: 'smooth' });

    this.route.url.subscribe(segments => {
      this.currentSection = segments[0]?.path || 'home';
      this.updateBannerImages();
    });

    this.loadContent();
  }

  loadContent(): void {
    this.loadContentCategory("APPLICATION");
    this.loadContentCategory("MUSIC");
    this.loadContentCategory("VIDEO");
    this.loadContentCategory("BOOK");
  }

  loadContentCategory(category: string): void {
    this.appsService.getContent(category, 0, 20, "rating").subscribe({
      next: (data) => {
        switch (category) {
          case 'APPLICATION': this.applications = data; break;
          case 'MUSIC': this.music = data; break;
          case 'VIDEO': this.videos = data; break;
          case 'BOOK': this.books = data; break;
        }
        this.isLoading = false;
      },
      error: (err) => {
        console.error(`Error cargando contenido (${category}):`, err);
        this.isLoading = false;
      }
    });
  }

  updateBannerImages(): void {
    this.images.forEach((image, index) => {
      image.currentSection = `${this.currentSection}${index + 1}`;
    });
  }

  getAppsByType(type: string): AppContent[] {
    switch (type) {
      case 'APPLICATION': return this.applications;
      case 'MUSIC': return this.music;
      case 'VIDEO': return this.videos;
      case 'BOOK': return this.books;
      default: return [];
    }
  }

  mapSectionTypeToCssClass(type: string): string {
    switch (type) {
      case 'APPLICATION': return 'section-bg-apps';
      case 'MUSIC': return 'section-bg-music';
      case 'VIDEO': return 'section-bg-videos';
      case 'BOOK': return 'section-bg-books';
      default: return '';
    }
  }

  mapSectionTypeToShadowClass(type: string): string {
    switch (type) {
      case 'APPLICATION': return 'apps-card';
      case 'MUSIC': return 'music-card';
      case 'VIDEO': return 'videos-card';
      case 'BOOK': return 'books-card';
      default: return '';
    }
  }

  isSectionActive(route: string): boolean {
    return this.router.url.includes(route);
  }
}
