import { Component, OnInit } from '@angular/core';
import { JavaServiceService } from '../java-service.service';
import { MatDialogConfig, MatDialog } from '@angular/material/dialog';
import { BookDeliverAddressComponent } from '../book-deliver-address/book-deliver-address.component';
import { AddAddressService } from '../share/add-address.service';
import { AuthenticationService } from '../service/authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {
  bookList:any;
  shipingCharge=0;

  constructor( private javaServiceObj:JavaServiceService,public addreqService:AddAddressService,
    public dialog:MatDialog,private hasLogin:AuthenticationService,private router:Router) { }

  getPrice(){
    this.shipingCharge=60
      this.javaServiceObj.totalPrice=this.shipingCharge;
      for (let index = 0; index < this.bookList.length; index++) {
        this.javaServiceObj.totalPrice=this.javaServiceObj.totalPrice+this.bookList[index].amount*this.bookList[index].quantity;  
      }
      // console.log(this.javaServiceObj.totalPrice.toPrecision(6));
  }
  plusQuantity(requestId:number){
    console.log("plus");
    this.javaServiceObj.plusQuantity(requestId);
  }
  minusQuantity(requestId:number){
    this.javaServiceObj.minusQuantity(requestId);
  }
  ngOnInit() {
    if(this.hasLogin.isUserLoggedIn()){
      this.javaServiceObj.getBuyBook().subscribe(
        book=>{
          this.bookList=book;
          console.log(this.bookList);
          console.log(this.bookList[0].quantity);
          this.getPrice();
        }
      );
     }
  }
  deleteBookRequest(requestId:number){
    // console.log(requestId);
    this.javaServiceObj.delteBookRequest(requestId).subscribe(
      data=>{
        this.router.navigateByUrl('/refresh', { skipLocationChange: true }).then(() => {
        this.javaServiceObj.getBookNotification();
        this.router.navigateByUrl('/checkout');
        }
        );
      }
    )
  }
  
  buyNow(){
    this.javaServiceObj.checkCart=true;
    this.addreqService.initializeFormGroup();
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "50%";
    this.dialog.open(BookDeliverAddressComponent,dialogConfig);
  }

}
