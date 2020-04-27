import { Component, OnInit } from '@angular/core';
import { JavaServiceService } from '../java-service.service';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { EditUserComponent } from '../edit-user/edit-user.component';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
  userList: any = [];
  constructor(public javaService: JavaServiceService,
    public dialog: MatDialog) { }

  ngOnInit() {
    this.javaService.userList().subscribe((user: any[]) => {
      this.userList = user;
      console.log(user);
    });
  }
  updateUser(userId: number) {
    console.log("update user called");
    this.javaService.userId = userId;
    console.log(userId);
    const editDialog = new MatDialogConfig();
    editDialog.disableClose = true;
    editDialog.autoFocus = true;
    editDialog.width = "50%";
    this.dialog.open(EditUserComponent, editDialog);
  }
  deleteUser(userId: number) {
    console.log("delete user called");
   // console.log(userId);
    //this.javaService.userId = userId;
    this.javaService.deleteUser(userId).subscribe(data=>{
      console.log(data);
    });
  }

}
