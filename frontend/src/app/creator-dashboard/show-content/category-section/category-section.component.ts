import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CarouselModule } from 'primeng/carousel';
import { ItemComponent } from '../../../explorer/item/item.component';
import { RouterLink } from '@angular/router';
@Component({
  selector: 'app-category-section',
  imports: [CommonModule, CarouselModule, ItemComponent, RouterLink],
  templateUrl: './category-section.component.html',
  styleUrl: './category-section.component.css'
})
export class CategorySectionComponent {
  @Input() title!: string;
  @Input() items: any[] = [];
  @Input() fromCreator: boolean = true;
  @Input() numVisible: number = 6;
  @Input() numScroll: number = 1;
  
  
  NgOnInit() {
    
  }
} 