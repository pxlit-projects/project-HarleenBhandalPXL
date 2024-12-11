import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Post} from "../models/post.model";

@Injectable({
  providedIn: 'root'
})
export class PostService {
  http: HttpClient = inject(HttpClient)
  api: string = 'http://localhost:8083/api/posts';
  constructor() {

  }

  getPosts(): Observable<Post[]> {
    return this.http.get<Post[]>(this.api);
  }

  createPost(post: Post): Observable<Post> {
    return this.http.post<Post>(this.api, post);
  }
}
