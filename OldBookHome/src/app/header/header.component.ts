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

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  cartshow:boolean=false;
  bookList:any;
  constructor(public dialog: MatDialog,public loginService:LoginServeiceService,public registrationService:RegistrationService,public javaCallObj:JavaServiceService,
    private hasLogin:AuthenticationService,private router:Router) { }
  fun(){
    this.cartshow=true;
  }
  ngOnInit() {
    
  }
  searchBook(searchValue:any){
    this.javaCallObj.searchBook(searchValue.value);
  }

  getNotificationBook(){
    if(this.hasLogin.isUserLoggedIn()){
      this.javaCallObj.getBuyBook().subscribe(
        book=>{
          this.bookList=book;
          console.log(this.bookList);
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

  //this method used for geeting all book request for deliver 
  deliverySellRequest(){
    this.router.navigate(["/deliverBuyRequest"]);
  }

  // this method used for getting all book request for buy (get delivery)
  deliveryRequestFun(){
    this.router.navigate(["/deliveryRequest"]);
  }

  listUserFun(){
    this.router.navigate(["/userList"]);
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

  // ---------------admin part---------------

  deliverySellRequestAdmin(){
    this.router.navigate(["/deliverBuyRequest"]);
  }
  deliveryRequestFunAdmin(){
    this.router.navigate(["/deliveryRequest"]);
  }
  
}
