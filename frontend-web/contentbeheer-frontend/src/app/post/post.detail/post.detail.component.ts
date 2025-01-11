import {Component, inject} from '@angular/core';
import {PostService} from "../../services/post/post.service";
import {Post} from "../../models/post.model";
import {ActivatedRoute, RouterLink} from "@angular/router";
import {DatePipe} from "@angular/common";
import {MatButton, MatIconButton} from "@angular/material/button";
import {FormBuilder, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {CommentService} from "../../services/post/comment.service";
import {Comment} from "../../models/comment.model";
import {MatIcon} from "@angular/material/icon";
import {AuthService} from "../../services/auth/auth.service";

@Component({
  selector: 'app-post.detail',
  standalone: true,
  imports: [
    DatePipe,
    FormsModule,
    MatLabel,
    MatFormField,
    MatInput,
    MatButton,
    ReactiveFormsModule,
    MatIcon,
    MatIconButton,
    RouterLink
  ],
  templateUrl: './post.detail.component.html',
  styleUrl: './post.detail.component.css'
})
export class PostDetailComponent {
  postId: number = -1;
  post: Post = {
    id: undefined,
    title: '',
    content: '',
    author: "",
    creationDate: "",
  };
  comments: Comment[] = [];

  postService: PostService = inject(PostService);
  commentService: CommentService = inject(CommentService);
  authService: AuthService = inject(AuthService);
  route: ActivatedRoute = inject(ActivatedRoute);
  fb: FormBuilder = inject(FormBuilder);

  commentForm = this.fb.group({
    postId: [this.postId],
    content: ['', Validators.required],
    username: ['', Validators.required]
  });

  constructor() {}

  ngOnInit() {
    this.postId = this.route.snapshot.params['id'];
    this.getPost();
    this.getComments();
  }

  getPost(): void {
    this.postService.getPost(this.postId).subscribe(post => {
      this.post = post;
    });
  }

  getComments(): void {
    this.commentService.getCommentsByPostId(this.postId).subscribe(comments => {
      this.comments = comments;
    });
  }

  onSubmit(): void {
    const comment: Comment = {
      postId: this.postId,
      content: this.commentForm.value.content,
      userName: this.commentForm.value.username
      };

    this.commentService.createComment(comment).subscribe(() => {
      this.getComments();
    });
  }

  deleteComment(id: number | undefined): void {
    this.commentService.deleteComment(id).subscribe(() => {
      this.getComments();
    });
  }
}
