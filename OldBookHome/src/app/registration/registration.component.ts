import { Component, OnInit } from '@angular/core';
import { RegistrationService } from '../share/registration.service';
import { LoginServeiceService } from '../share/login-serveice.service';
import { MatDialogRef, MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { LoginComponent } from '../login/login.component';
import { NotificationService } from '../share/notification.service';
import { UserInfo, JavaServiceService } from '../java-service.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
  userInfo=new UserInfo();
  constructor(public registrationService:RegistrationService,
    public loginService:LoginServeiceService,
    public notificationService:NotificationService,
    public dialogRef:MatDialogRef<RegistrationComponent>,
    public dialog: MatDialog,
    public javaServiceObj:JavaServiceService  ) {    
  }

onClear() {
this.registrationService.form.reset();
this.registrationService.initializeFormGroup();
  this.notificationService.warn(':: Clear successfully');
}

onSubmit() {
if (this.registrationService.form.valid) {

  this.userInfo=this.registrationService.form.value;
  this.javaServiceObj.register(this.userInfo).subscribe(
    data=>{
      console.log("register ho gaya.......");
      console.log(this.userInfo);
      this.registrationService.form.reset();
      this.registrationService.initializeFormGroup();
      this.notificationService.success(':: Submitted successfully');
      this.onClose();
    },error=>{
      // this.invalidLogin=true;
      // this.router.navigate(['/loginError']);
      console.log("not Registation");
      this.registrationService.form.reset();
      this.registrationService.initializeFormGroup();
      this.notificationService.success(':: Registation Failed');
      this.onClose();
      }
  );
}
}

onClose() {
this.registrationService.form.reset();
this.registrationService.initializeFormGroup();
this.dialogRef.close();
}

// login(){
// this.onClose();
// this.loginService.initializeFormGroup();
// const dialogConfig = new MatDialogConfig();
// dialogConfig.disableClose = true;
// dialogConfig.autoFocus = true;
// dialogConfig.width = "40%";
//  this.dialog.open(LoginComponent,dialogConfig);
// }

  ngOnInit() {
  }

}
