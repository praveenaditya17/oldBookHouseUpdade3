import { Component, OnInit } from '@angular/core';
import { UserInfo, JavaServiceService } from '../java-service.service';
import { AddAddressService } from '../share/add-address.service';
import { NotificationService } from '../share/notification.service';
import { MatDialogRef, MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-book-deliver-address',
  templateUrl: './book-deliver-address.component.html',
  styleUrls: ['./book-deliver-address.component.css']
})
export class BookDeliverAddressComponent implements OnInit {

  userInfo=new UserInfo();
  isShowAddress:boolean=false;
  address:any;
  lastAddress:any;
  addressId:number;

  constructor(
    public addaddressservice:AddAddressService,
    public notificationService:NotificationService,
    public dialogRef:MatDialogRef<BookDeliverAddressComponent>,
    public dialog: MatDialog,
    public javaServiceObj:JavaServiceService) { }

  ngOnInit(){
    this.javaServiceObj.getAddress().subscribe(
      (data)=>{
        this.address=data;
        this.address=this.address.address;
        // console.log(this.address);
      });
  }

  onClear() {
    this.addaddressservice.form.reset();
    this.addaddressservice.initializeFormGroup();
    this.notificationService.warn(':: Clear successfully');
  }
  sendId(addressId:number){
    // this.javaServiceObj.bookObj.addressId=id;
    console.log(this.javaServiceObj.checkCart);
    console.log(addressId);
    if(this.javaServiceObj.checkCart){
      this.javaServiceObj.bookDeliverAddressMultipleBook(addressId);
    }else{
      this.javaServiceObj.bookDeliverAddressSingleBook(addressId); 
    }
    this.onClose();
  }
  onSubmit() {
    if (this.addaddressservice.form.valid) {
      this.userInfo=this.addaddressservice.form.value;
      this.javaServiceObj.addAddress(this.userInfo).subscribe(
        data=>{
          this.lastAddress=data;
          this.addressId=this.lastAddress.address[this.lastAddress.address.length-1].id;
          console.log(this.addressId);
          this.sendId(this.addressId);
        });
      this.addaddressservice.form.reset();
      this.addaddressservice.initializeFormGroup();
      this.notificationService.success(':: Submitted successfully');
      this.onClose();
    }
  }
    
  onClose() {
    this.addaddressservice.form.reset();
    this.addaddressservice.initializeFormGroup();
    this.dialogRef.close();
  }
  addAddress(){
    this.isShowAddress=true;
  }


}
