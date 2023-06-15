import { Patient } from 'src/app/models/patient.model';
import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class PatientService {
  constructor(private _httpClient: HttpClient) {}

  private baseUrl = 'http://localhost:8080/patient/api';

  getAllPatient(): Observable<Patient[]> {
    return this._httpClient.get<Patient[]>(this.baseUrl);
  }

  getPatientById(id: any): Observable<Patient> {
    return this._httpClient.get<Patient>(`${this.baseUrl}/${id}`);
  }

  createPatient(data: any): Observable<any> {
    return this._httpClient.post(this.baseUrl, data);
  }

  updatePatient(id: any, data: any): Observable<any> {
    return this._httpClient.put(`${this.baseUrl}/${id}`, data);
  }

  deletePatient(id: any): Observable<any> {
    return this._httpClient.delete(`${this.baseUrl}/${id}`);
  }

  findPatientByName(name: any): Observable<Patient[]> {
    return this._httpClient.get<Patient[]>(`${this.baseUrl}?name=${name}`);
  }

  findPatientByPid(pid: any): Observable<Patient[]> {
    return this._httpClient.get<Patient[]>(`${this.baseUrl}?pid=${pid}`);
  }

  findPatientPagination(page: any, size: any): Observable<any> {
    return this._httpClient.get<any>(
      `${this.baseUrl}/page?page=${page}&size=${size}`
    );
  }

  findPatientByNamePagination(
    name: any,
    page: any,
    size: any
  ): Observable<any> {
    return this._httpClient.get<any>(
      `${this.baseUrl}/page?page=${page}&size=${size}&name=${name}`
    );
  }

  findPatientByPidPagination(pid: any, page: any, size: any): Observable<any> {
    return this._httpClient.get<any>(
      `${this.baseUrl}/page?page=${page}&size=${size}&pid=${pid}`
    );
  }
}
