import { Component, OnInit } from '@angular/core';
import { JavaServiceService } from '../java-service.service';
import { MatDialogConfig, MatDialog } from '@angular/material/dialog';
import { UpdateBookStatusComponent } from '../update-book-status/update-book-status.component';
import { UpdateBookStatusService } from '../share/update-book-status.service';

@Component({
  selector: 'app-delivery-request',
  templateUrl: './delivery-request.component.html',
  styleUrls: ['./delivery-request.component.css']
})
export class DeliveryRequestComponent implements OnInit {

  array:any;
  sucess:boolean=true;

  constructor(public dialog: MatDialog,private  javaServiceObj:JavaServiceService,private updateService:UpdateBookStatusService) { }

  ngOnInit() {
    this.javaServiceObj.getDeliveryRequest().subscribe(
      data=>{
        this.array=data;
      }
    );
  }
  updateStatus(bookId:number){
    // console.log(this.array[bookId][0]);
    this.javaServiceObj.bookId=this.array[bookId][0];
    this.updateService.initializeFormGroup();
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "40%";
    this.dialog.open(UpdateBookStatusComponent,dialogConfig);
  }

}
