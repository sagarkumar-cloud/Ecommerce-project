import { Routes } from '@angular/router';
import { ProductListComponent } from './components/product-list/product-list.component';
import { CartdetailsComponent } from './components/cartdetails/cartdetails.component';
import { CheckoutComponent } from './components/checkout/checkout.component';

export const routes: Routes = [
    
    { path: 'category/:id', component: ProductListComponent },
    { path: 'product', component: ProductListComponent },
    { path: '', redirectTo: '/products', pathMatch: 'full' },
    { path: 'search/:keyword', component: ProductListComponent},
    { path: 'cart-details', component:CartdetailsComponent},
    { path: 'checkout', component:CheckoutComponent }
];
