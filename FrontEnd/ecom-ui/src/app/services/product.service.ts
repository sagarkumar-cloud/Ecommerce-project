import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Appconstant } from '../../appconstant';
import { ActivatedRoute } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private httpClient = inject(HttpClient);


  getProductByCategoryId(categoryId:number) {
  return this.httpClient.get<any>(`${Appconstant.GET_PRODUCT_BY_CATEGORY_ID}${categoryId}`);
  }

  getAllCategories(){
    return this.httpClient.get<any>(`${Appconstant.ALL_CATEGORIES_ENDPOINT}`);
  }

  searchProducts(keyword: string){
    return this.httpClient.get<any>(`${Appconstant.GET_PRODUCT_BY_PRODUCTNAME}${keyword}`);
  }
}
