import { Component, OnInit } from '@angular/core';
import { ForgetPasswordService } from '../share/forget-password.service';
import { MatDialogRef } from '@angular/material/dialog';
import { JavaServiceService } from '../java-service.service';

@Component({
  selector: 'app-forget-password',
  templateUrl: './forget-password.component.html',
  styleUrls: ['./forget-password.component.css']
})
export class ForgetPasswordComponent implements OnInit {

  user:any;
  constructor(public forgetPasswordService:ForgetPasswordService, 
    public dialogRef: MatDialogRef<ForgetPasswordComponent>,
    public javaServiceObj:JavaServiceService
    ) { }
  ngOnInit() {
  }
  onSubmit(){
    if (this.forgetPasswordService.form.valid) {

      this.user=this.forgetPasswordService.form.value;
      console.log(this.user.userName);
      this.javaServiceObj.forgetPassword(this.user.userName);
    }
    this.forgetPasswordService.form.reset();
    this.forgetPasswordService.initializeFormGroup();
    this.onClose();
  }
  onClose() {
    this.forgetPasswordService.form.reset();
    this.forgetPasswordService.initializeFormGroup();
    this.dialogRef.close();
  }
  

}
