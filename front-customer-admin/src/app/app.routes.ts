import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';

export const routes: Routes = [
    {
        path: '',
        redirectTo: '/clientes',
        pathMatch: 'full'
    },
    {
        path: 'clientes',
        component: HomeComponent
    }
];
