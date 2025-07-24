import { Component, OnInit } from '@angular/core';
import { FormsModule,FormBuilder,ReactiveFormsModule ,FormGroup} from '@angular/forms';
import { RouterLink, RouterModule } from '@angular/router';

@Component({
  selector: 'app-checkout',
  standalone:true,
  imports: [FormsModule,ReactiveFormsModule,RouterLink,RouterModule],
  templateUrl: './checkout.component.html',
  styleUrl: './checkout.component.css'
})
export class CheckoutComponent implements OnInit{

  checkoutFormGroup!: FormGroup;

  totalPrice: number = 0;
  totalQuantity: number = 0;

  constructor(private formBuilder: FormBuilder) { }


  ngOnInit(): void {

    this.checkoutFormGroup = this.formBuilder.group({
      customer: this.formBuilder.group({
        name: [''],
        email: [''],
        phno: ['']
      }),

      shippingAddress: this.formBuilder.group({
        street: [''],
        city: [''],
        state: [''],
        hno: [''],
        zipCode: ['']
      })
    })

  }

  onSubmit() {
    console.log(this.checkoutFormGroup.get('customer')!.value);
    console.log(this.checkoutFormGroup.get('shippingAddress')!.value);
  }

}

