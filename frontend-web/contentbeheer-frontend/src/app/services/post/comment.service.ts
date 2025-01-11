import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Comment} from "../../models/comment.model";

@Injectable({
  providedIn: 'root'
})
export class CommentService {
  http: HttpClient = inject(HttpClient)
  api: string = 'http://localhost:8083/comment-service/api/comments';

  constructor() {
  }

  getCommentsByPostId(postId: number): Observable<Comment[]> {
    return this.http.get<Comment[]>(`${this.api}/post/${postId}`);
  }

  createComment(comment: Comment): Observable<Comment> {
    console.log(comment.content, comment.postId, comment.userName);

    return this.http.post<Comment>(this.api, comment);
  }

  deleteComment(id: number | undefined): Observable<Comment> {
    return this.http.delete<Comment>(`${this.api}/${id}`);
  }

  updateComment(id: number, comment: string | null | undefined): Observable<Comment> {
    return this.http.put<Comment>(`${this.api}/${id}`, { content: comment });
  }

  getComment(id: number): Observable<Comment> {
    return this.http.get<Comment>(`${this.api}/${id}`);
  }
}
