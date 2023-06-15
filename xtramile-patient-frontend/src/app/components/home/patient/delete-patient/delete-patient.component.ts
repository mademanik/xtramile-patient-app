import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { PatientService } from 'src/app/services/patient/patient.service';

@Component({
  selector: 'app-delete-patient',
  templateUrl: './delete-patient.component.html',
  styleUrls: ['./delete-patient.component.scss'],
})
export class DeletePatientComponent {
  id: String = '';
  constructor(
    @Inject(MAT_DIALOG_DATA)
    public data: { id: any },
    private patientService: PatientService,
    private dialogRef: MatDialogRef<DeletePatientComponent>
  ) {}

  deleteProduct() {
    console.log(this.data.id);

    this.patientService.deletePatient(this.data.id).subscribe({
      next: (res) => {
        this.dialogRef.close({
          message: 'success',
        });
      },
      error: (err) => {
        this.dialogRef.close({
          message: 'error',
        });
        console.log(err);
      },
    });
  }
}
