import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { PatientService } from 'src/app/services/patient/patient.service';

@Component({
  selector: 'app-show-patient',
  templateUrl: './show-patient.component.html',
  styleUrls: ['./show-patient.component.scss'],
})
export class ShowPatientComponent implements OnInit {
  row: any;

  constructor(
    @Inject(MAT_DIALOG_DATA)
    public data: { id: any },
    private patientService: PatientService,
    private dialogRef: MatDialogRef<ShowPatientComponent>
  ) {}

  ngOnInit(): void {
    this.getPatientById(this.data.id);
  }

  getPatientById(id: String) {
    console.log(id);
    this.patientService.getPatientById(id).subscribe({
      next: (res) => {
        this.row = res;
      },
      error: (err) => {
        alert(err);
      },
    });
  }
}
