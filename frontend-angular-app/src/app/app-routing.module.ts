import {RouterModule, Routes} from '@angular/router';
import {AppLayoutComponent} from './layout/app-layout/app-layout.component';
import {NgModule} from '@angular/core';
import {StockListComponent} from "./componant/stock-list/stock-list.component";


const routes: Routes = [

    // Site routes
    {
        path: '',
        component: AppLayoutComponent,
        children: [
            {path: '', component: StockListComponent, pathMatch: 'full'}
        ]
    },

    // otherwise redirect to home
    {
        path: '**', redirectTo: ''
    }
];

@NgModule({
    imports: [
        RouterModule.forRoot(routes, {useHash: true})
    ],
    exports: [
        RouterModule
    ],
    declarations: []
})

export class AppRoutingModule {
}

