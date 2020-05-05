import { Component, OnInit } from '@angular/core';
import { LoginServeiceService } from '../share/login-serveice.service';
import { MatDialogRef, MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { RegistrationComponent } from '../registration/registration.component';
import { RegistrationService } from '../share/registration.service';
import { UserInfo, UserLogin, JavaServiceService } from '../java-service.service';
import { AuthenticationService } from '../service/authentication.service';
import { Router, NavigationEnd } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  // userInfo=new UserInfo();
  userLogin=new UserLogin();

  constructor(public loginService: LoginServeiceService,
    public registrationService: RegistrationService,
    public dialogRef: MatDialogRef<LoginComponent>,
    private javaCallObj:JavaServiceService,
    public dialog: MatDialog,
    private loginAuth:AuthenticationService,
    private router:Router) {

  }

  ngOnInit(): void {
  }

  onSubmit() {
    if (this.loginService.form.valid) {

        this.userLogin=this.loginService.form.value;
        this.loginAuth.authenticate(this.userLogin.userName,this.userLogin.userPassword).subscribe(
        data=>{
          sessionStorage.setItem('username',this.userLogin.userName);
          let tokenStr= 'Bearer '+data.token;
          sessionStorage.setItem('token', tokenStr);      
          this.router.navigate(['/mainslider']);
          console.log("user Login sucessfully.........");
          this.javaCallObj.getRole();
          this.javaCallObj.getBookNotification();
        },
        error=>{
        // this.invalidLogin=true;
        // this.router.navigate(['/loginError']);
        console.log("not logIn");
        //this.closebutton.nativeElement.click();
        }
        );

      // console.log(this.loginService.form.value);
      this.loginService.form.reset();
      this.loginService.initializeFormGroup();
      //console.log(this.loginService.form.value);
      this.onClose();
    }
  }

  onClose() {
    this.loginService.form.reset();
    this.loginService.initializeFormGroup();
    this.dialogRef.close();
  }
  registration() {
    this.onClose();
    this.registrationService.initializeFormGroup();
    const registratinDialog = new MatDialogConfig();
    registratinDialog.disableClose = true;
    registratinDialog.autoFocus = true;
    registratinDialog.width = "60%";
    this.dialog.open(RegistrationComponent, registratinDialog);
  }
}
