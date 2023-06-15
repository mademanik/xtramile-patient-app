import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Patient } from 'src/app/models/patient.model';
import { PatientService } from 'src/app/services/patient/patient.service';
import { Router } from '@angular/router';

import { AddPatientComponent } from './add-patient/add-patient.component';
import { DeletePatientComponent } from './delete-patient/delete-patient.component';
import { EditPatientComponent } from './edit-patient/edit-patient.component';
import { ShowPatientComponent } from './show-patient/show-patient.component';

@Component({
  selector: 'app-patient',
  templateUrl: './patient.component.html',
  styleUrls: ['./patient.component.scss'],
})
export class PatientComponent implements AfterViewInit, OnInit {
  displayedColumns: string[] = [
    'pid',
    'firstName',
    'lastName',
    'dateOfBirth',
    'gender',
    'address',
    'phoneNumber',
    'action',
  ];

  dataSource = new MatTableDataSource<Patient>();
  ELEMENT_DATA = [];

  @ViewChild(MatPaginator) paginator: any = MatPaginator;
  @ViewChild(MatSort) sort: any = MatSort;

  constructor(
    public dialog: MatDialog,
    private patientService: PatientService,
    private _snackBar: MatSnackBar,
    private router: Router
  ) {}

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  applyFilterName(event: Event) {
    const filterValueName = (event.target as HTMLInputElement).value;
    console.log(filterValueName);

    this.patientService.findPatientByName(filterValueName).subscribe({
      next: (res) => {
        this.dataSource.data = res;
      },
      error: (err) => {
        alert(err);
      },
    });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.patientService.findPatientByPid(filterValue).subscribe({
      next: (res) => {
        this.dataSource.data = res;
      },
      error: (err) => {
        alert(err);
      },
    });
  }

  ngOnInit(): void {
    this.getPatients();
  }

  getPatients() {
    this.patientService.getAllPatient().subscribe({
      next: (res) => {
        this.dataSource.data = res;
      },
      error: (err) => {
        alert(err);
      },
    });
  }

  addDialog() {
    const dialogRef = this.dialog.open(AddPatientComponent, {
      width: '50%',
      position: { top: '20px' },
    });
    dialogRef.afterClosed().subscribe((result) => {
      this.getPatients();
      if (result?.message == 'success') {
        this.openSnackbarSuccess('Success', 'Product successfully created');
      } else if (result?.message == 'error') {
        this.openSnackbarError('Error', 'Product create Failed');
      } else if (result?.message == 'invalid') {
        this.openSnackbarError('Error', 'Form invalid');
      }
    });
  }

  showDialog(id: String) {
    const dialogRef = this.dialog.open(ShowPatientComponent, {
      width: '50%',
      position: { top: '20px' },
      data: { id: id },
    });
    dialogRef.afterClosed().subscribe((result) => {
      this.getPatients();
    });
  }

  editDialog(id: String) {
    const dialogRef = this.dialog.open(EditPatientComponent, {
      width: '50%',
      position: { top: '20px' },
      data: { id: id },
    });
    dialogRef.afterClosed().subscribe((result) => {
      this.getPatients();
      if (result?.message == 'success') {
        this.openSnackbarSuccess('Success', 'Product successfully updated');
      } else if (result?.message == 'error') {
        this.openSnackbarError('Error', 'Product create Failed');
      } else if (result?.message == 'invalid') {
        this.openSnackbarError('Error', 'Form invalid');
      }
    });
  }

  deleteDialog(id: String) {
    const dialogRef = this.dialog.open(DeletePatientComponent, {
      width: '20%',
      position: { top: '20px' },
      data: { id: id },
    });
    dialogRef.afterClosed().subscribe((result) => {
      this.getPatients();
      if (result?.message == 'success') {
        this.openSnackbarSuccess('Success', 'Product deleted successfully');
      } else if (result?.message == 'error') {
        this.openSnackbarError('Error', 'Product create Failed');
      } else if (result?.message == 'invalid') {
        this.openSnackbarError('Error', 'Form invalid');
      }
    });
  }

  openSnackbarSuccess(title: string, message: string) {
    this._snackBar.open(message, title, {
      horizontalPosition: 'right',
      verticalPosition: 'top',
      duration: 3000,
      panelClass: 'app-notification-success',
    });
  }

  openSnackbarError(title: string, message: string) {
    this._snackBar.open(message, title, {
      horizontalPosition: 'right',
      verticalPosition: 'top',
      duration: 3000,
      panelClass: 'app-notification-error',
    });
  }
}
