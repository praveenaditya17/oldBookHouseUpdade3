import { Component, OnInit } from '@angular/core';
import { MatDialogConfig, MatDialog } from '@angular/material/dialog';
import { LoginComponent } from '../login/login.component';
import { LoginServeiceService } from '../share/login-serveice.service';
import { RegistrationService } from '../share/registration.service';
import { RegistrationComponent } from '../registration/registration.component';
import { JavaServiceService } from '../java-service.service';
import { AuthenticationService } from '../service/authentication.service';
import { Router } from '@angular/router';
import { ProfileComponent } from '../profile/profile.component';
import { NgxSpinnerService } from "ngx-spinner";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  cartshow:boolean=false;
  bookList:any;
  nav_toggel:boolean=false;
  constructor(public dialog: MatDialog,public loginService:LoginServeiceService,public registrationService:RegistrationService,public javaCallObj:JavaServiceService,
    private hasLogin:AuthenticationService,private router:Router,public spinner:NgxSpinnerService) { }
  fun(){
    this.cartshow=true;
  }
  ngOnInit() {
  //   this.spinner.show();
  //   setTimeout(() => {
  //   /** spinner ends after 5 seconds */
  //   this.spinner.hide();
  // }, 3000);
  }
  home(){
    this.javaCallObj.getSpinner();
    this.router.navigateByUrl('/refresh', { skipLocationChange: true }).then(() => {
      this.router.navigateByUrl('mainslider');
    });
  }

  // purchaseBook(){
  //   this.spinner.show();
  //   setTimeout(() => {
  //   this.spinner.hide();
  //   }, 1000);
  //   this.router.navigate(['/checkout']);
  // }

  searchBook(searchValue:any){
    this.javaCallObj.searchBook(searchValue.value);
  }
  getPrice(){
    this.javaCallObj.totalPrice=0;
    for (let index = 0; index < this.bookList.length; index++) {
      this.javaCallObj.totalPrice=this.javaCallObj.totalPrice+this.bookList[index].amount*this.bookList[index].quantity;  
    }
  }

  getNotificationBook(){
    if(this.hasLogin.isUserLoggedIn()){
      this.javaCallObj.getBuyBook().subscribe(
        book=>{
          this.bookList=book;
          console.log(this.bookList);
          this.getPrice();
        }
      );
     }
  }
  deleteBookRequest(requestId:number){
    // console.log(requestId);
    this.javaCallObj.delteBookRequest(requestId).subscribe(
      data=>{
          this.javaCallObj.getBookNotification();
      }
    )
  }

// -------------------menue desing --------------------------


searchCatogory(bookCatagory:any){
  this.javaCallObj.searchCatagory(bookCatagory);
}
  //this method used for geeting all book request for deliver 
  deliverySellRequest(){
    this.javaCallObj.getSpinner();
    this.router.navigate(["/deliverBuyRequest"]);
  }

  // this method used for getting all book request for buy (get delivery)
  deliveryRequestFun(){
    this.javaCallObj.getSpinner();
    this.router.navigate(["/deliveryRequest"]);
  }

  login(){
    this.loginService.initializeFormGroup();
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "40%";
    this.dialog.open(LoginComponent,dialogConfig);
  }
  registration(){
    this.registrationService.initializeFormGroup();
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "60%";
    this.dialog.open(RegistrationComponent,dialogConfig);
  }
  viewProfile(){
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "40%";
    //{position: {top: '0%', left: '20%'}}
   // dialogConfig.position({ top: '50px', left: '50px' });
    this.dialog.open(ProfileComponent,dialogConfig);
  }
  logOut(){
    this.hasLogin.logOut();
    sessionStorage.removeItem('userRole');
    sessionStorage.removeItem('notification1');
    sessionStorage.setItem('notification1','0');
    this.router.navigate(["/mainslider"]);

  }
  //........Hystory Part...........

  buyOrder(){
    // this.spinner.show();
    // setTimeout(() => {
    // this.spinner.hide();
    // }, 2000);
    this.javaCallObj.getSpinner();
    this.router.navigate(["/buyHistory"]);
  }
  sellOrder(){
    // this.spinner.show();
    // setTimeout(() => {
    // this.spinner.hide();
    // }, 2000);
    this.javaCallObj.getSpinner();
    this.router.navigate(["/sellHistory"]);
  }

  // ---------------admin part---------------

  listUserFun(){
    // this.spinner.show();
    // setTimeout(() => {
    // this.spinner.hide();
    // }, 2000);
    this.javaCallObj.getSpinner();
    this.router.navigate(["/userList"]);
  }

  deliverySellRequestAdmin(){
    // this.spinner.show();
    // setTimeout(() => {
    // this.spinner.hide();
    // }, 2000);
    this.javaCallObj.getSpinner();
    this.router.navigate(["/deliverBuyRequest"]);
  }
  deliveryRequestFunAdmin(){
    // this.spinner.show();
    // setTimeout(() => {
    // this.spinner.hide();
    // }, 2000);
    this.javaCallObj.getSpinner();
    this.router.navigate(["/deliveryRequest"]);
  }
  updateBookPrice(){
    this.router.navigateByUrl('/refresh', { skipLocationChange: true }).then(() => {
      this.javaCallObj.oldBookStatus=false;
      this.router.navigate(['/updateBookPrice']);
    });
   
  }
  getOldBook(){
    this.router.navigateByUrl('/refresh', { skipLocationChange: true }).then(() => {
    this.javaCallObj.oldBookStatus=true;
    this.router.navigate(['/updateBookPrice']);
    });
  }

  contact(){
    this.router.navigate(['/contact']);
    this.javaCallObj.getSpinner();
  }
}
