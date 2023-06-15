import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PatientService } from 'src/app/services/patient/patient.service';

interface Gender {
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-edit-patient',
  templateUrl: './edit-patient.component.html',
  styleUrls: ['./edit-patient.component.scss'],
})
export class EditPatientComponent implements OnInit {
  genders: Gender[] = [
    { value: 'Male', viewValue: 'Male' },
    { value: 'Female', viewValue: 'Female' },
  ];

  constructor(
    @Inject(MAT_DIALOG_DATA)
    public data: { id: any },
    private formBuilder: FormBuilder,
    private patientService: PatientService,
    private dialogRef: MatDialogRef<EditPatientComponent>
  ) {}

  patientForm: FormGroup = this.formBuilder.group({
    pid: ['', Validators.required],
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    dateOfBirth: ['', Validators.required],
    gender: ['', Validators.required],
    address: ['', Validators.required],
    suburb: ['', Validators.required],
    state: ['', Validators.required],
    postcode: ['', Validators.required],
    phoneNumber: ['', Validators.required],
  });

  ngOnInit(): void {
    this.getProductById(this.data.id);
  }

  getProductById(id: String) {
    this.patientService.getPatientById(id).subscribe({
      next: (res) => {
        console.log(res);

        const {
          pid,
          firstName,
          lastName,
          dateOfBirth,
          gender,
          address,
          suburb,
          state,
          postcode,
          phoneNumber,
        } = res;

        this.patientForm.setValue({
          pid: pid,
          firstName: firstName,
          lastName: lastName,
          dateOfBirth: dateOfBirth,
          gender: gender,
          address: address,
          suburb: suburb,
          state: state,
          postcode: postcode,
          phoneNumber: phoneNumber,
        });
      },
      error: (err) => {
        alert(err);
      },
    });
  }

  submit(): void {
    if (this.patientForm.valid) {
      const getPid = this.patientForm.get('pid')?.value;

      this.patientService
        .updatePatient(getPid, this.patientForm.value)
        .subscribe({
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
    } else {
      this.dialogRef.close({
        message: 'invalid',
      });
    }
  }
}
