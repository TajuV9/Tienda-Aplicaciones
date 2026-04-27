import { Routes } from '@angular/router';
import { LoginComponent } from './my-account/authentification/login/login.component';
import { RegisterComponent } from './my-account/authentification/register/register.component';
import { LoginGuardian } from './my-account/authentification/login/login-guardian';
import { ArchiveComponent } from './explorer/archive/archive.component';
import { AccountManagerComponent } from './my-account/account-manager/account-manager.component';
import { ItemSectionComponent } from './explorer/item-section/item-section.component';
import { TosComponent } from './my-account/tos/tos.component';
import { SearchContentComponent } from './search-bar/search-content/search-content.component';
import { CreatorZoneComponent } from './creator-dashboard/creator-zone/creator-zone.component';
import { MyDownloadsComponent } from './my-account/my-downloads/my-downloads.component';
import { HomeComponent } from './explorer/home/home.component';
import { SectionComponent } from './explorer/sections/section.component';

export const routes: Routes = [
    { path: '', redirectTo: '/home', pathMatch: 'full' },
    { path: 'home', component: HomeComponent },
    { path: 'application', component: SectionComponent },
    { path: 'books', component: SectionComponent },
    { path: 'videos', component: SectionComponent },
    { path: 'music', component: SectionComponent },
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'my-account', component: AccountManagerComponent, canActivate: [LoginGuardian] },
    { path: 'my-downloads', component: MyDownloadsComponent, canActivate: [LoginGuardian] },
    { path: 'archive', component: ArchiveComponent },
    { path: 'archive/:id', component: ArchiveComponent },
    { path: 'section/:currentSection/:type', component: ItemSectionComponent },
    { path: 'terms-and-conditions', component: TosComponent },
    { path: 'search/:searchContent', component: SearchContentComponent },
    { path: 'creator-zone', component: CreatorZoneComponent, canActivate: [LoginGuardian] },
    { path: '**', redirectTo: '/home', pathMatch: 'full' }

];
