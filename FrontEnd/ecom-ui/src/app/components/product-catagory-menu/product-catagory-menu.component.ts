import { Component, OnInit,inject } from '@angular/core';
import { ProductCatagory } from '../../dto/product-catagory';
import { ProductService } from '../../services/product.service';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
@Component({
  selector: 'app-product-catagory-menu',
  standalone:true,
  imports: [CommonModule,RouterLink],
  templateUrl: './product-catagory-menu.component.html',
  styleUrl: './product-catagory-menu.component.css'
})
export class ProductCatagoryMenuComponent implements OnInit{

  productCatagories:ProductCatagory[]=[]
   service=inject(ProductService);

  ngOnInit(): void {
    this.allCatogories();
  }

  allCatogories(){
      this.service.getAllCategories().subscribe(response=>{
        this.productCatagories=response.data;
      })
  }
}
