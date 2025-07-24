import { Component } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { ProductListComponent } from './components/product-list/product-list.component';
import { ProductCatagoryMenuComponent } from './components/product-catagory-menu/product-catagory-menu.component';
import { SerachComponent } from './components/serach/serach.component';
import { CartstatusComponent } from './components/cartstatus/cartstatus.component';

@Component({
  selector: 'app-root',
  imports: [SerachComponent,
    RouterLink,
    RouterOutlet,
    ProductListComponent,
    ProductCatagoryMenuComponent,CartstatusComponent],
    
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'ecom-ui';
}
