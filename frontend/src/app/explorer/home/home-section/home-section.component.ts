import { Component, Input } from '@angular/core';
import { AppContent } from '../../apps/apps.service';
import { ItemComponent } from '../../item/item.component';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-homesection',
  templateUrl: './home-section.component.html',
  styleUrls: ['./home-section.component.css'],
  imports: [ItemComponent, RouterLink, CommonModule]
})
export class HomeSectionComponent {
  @Input() displayedApplications: any[] = [];
  @Input() sectionType: string = '';

  ngOnInit(): void {
  }

  

}
