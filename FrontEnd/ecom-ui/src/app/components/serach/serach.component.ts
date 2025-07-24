import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
@Component({
  selector: 'app-serach',
  standalone: true,
  imports: [],
  templateUrl: './serach.component.html',
  styleUrl: './serach.component.css'
})
export class SerachComponent {

  constructor(private router: Router) { }

  doSearch(value: string) {
    this.router.navigateByUrl(`/search/${value}`);
  }
}
