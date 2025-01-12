import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Post} from "../../models/post.model";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class PostService {
  http: HttpClient = inject(HttpClient)
  // api: string = 'http://localhost:8083/post-service/api/posts';
  api: string = environment.apiUrl + '/post-service/api/posts';

  constructor() {

  }

  getPublishedPosts(): Observable<Post[]> {
    return this.http.get<Post[]>(this.api);
  }

  getConceptPosts(): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.api}/concepts`);
  }

  getPendingPosts(): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.api}/pending`);
  }

  getRejectedPosts(): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.api}/rejected`);
  }

  createPost(post: Post): Observable<Post> {
    const headers = new HttpHeaders().set('Role', 'editor');
    return this.http.post<Post>(this.api, post, {headers});
  }

  savePostAsConcept(post: Post): Observable<Post> {
    const headers = new HttpHeaders().set('Role', 'editor');
    return this.http.post<Post>(`${this.api}/concepts/add`, post, {headers});
  }

  editPost(id: number, post: Post): Observable<Post> {
    const headers = new HttpHeaders().set('Role', 'editor');
    return this.http.put<Post>(`${this.api}/${id}/edit`, post, {headers});
  }

  getPost(id: number): Observable<Post> {
    return this.http.get<Post>(`${this.api}/${id}`);
  }

  updateToPending(id: number | undefined): Observable<Post> {
    const headers = new HttpHeaders().set('Role', 'editor');
    return this.http.put<Post>(`${this.api}/${id}/pending`, {}, {headers});
  }
}
