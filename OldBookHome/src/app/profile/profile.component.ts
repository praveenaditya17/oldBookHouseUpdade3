import { Component, OnInit } from '@angular/core';
import { JavaServiceService, UserInfo } from '../java-service.service';
import { MatDialogRef } from '@angular/material/dialog';


@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  userInfo=new UserInfo();
  profile:any;
  name:string;
  email:string;
  mobile:string;
  constructor( private javaServiceObj:JavaServiceService,private dialogRef:MatDialogRef<ProfileComponent>) { }

  ngOnInit() {
    this.javaServiceObj.getAddress().subscribe(
      (data)=>{
        this.profile=data;
        this.name=this.profile.firstName+" "+this.profile.lastName;
        this.email=this.profile.email;
        this.mobile=this.profile.mobileNumber;
      });
  }
  onClose() {
    this.dialogRef.close();
  }

}
