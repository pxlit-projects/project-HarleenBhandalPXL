import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Post} from "../../models/post.model";

@Injectable({
  providedIn: 'root'
})
export class ReviewService {
  http: HttpClient = inject(HttpClient)
  api: string = 'http://localhost:8083/review-service/api/reviews';

  constructor() { }

  approvePost(id: number | undefined): Observable<Post> {
    const headers = new HttpHeaders().set('Role', 'editor');
    return this.http.get<Post>(`${this.api}/post/${id}/approve`, {headers});
  }

  rejectPost(id: number, comment: string, author: string): Observable<Post> {
    const headers = new HttpHeaders().set('Role', 'editor');
    const payload = {comment, author};
    return this.http.put<Post>(`${this.api}/post/${id}/reject`, payload, {headers});
  }
}
