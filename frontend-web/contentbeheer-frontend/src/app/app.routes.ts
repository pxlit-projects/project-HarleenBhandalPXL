import { Routes } from '@angular/router';
import {CreatePostComponent} from "./post/create/create-post/create-post.component";
import {PostComponent} from "./post/post.component";
import {ConceptComponent} from "./post/concept/concept.component";
import {EditComponent} from "./post/edit/edit.component";
import {PendingComponent} from "./post/pending/pending.component";
import {RejectComponent} from "./post/reject/reject.component";
import {RejectedComponent} from "./post/reject/rejected/rejected.component";
import {PostDetailComponent} from "./post/post.detail/post.detail.component";
import {CommentEditComponent} from "./post/comment/comment.edit/comment.edit.component";
import {NotificationComponent} from "./notification/notification/notification.component";

export const routes: Routes = [
  {
    path: 'posts',
    component: PostComponent
  },
  {
    path: 'create-post',
    component: CreatePostComponent
  },
  {
    path: 'concepts',
    component: ConceptComponent
  },
  {
    path: 'posts/:id/edit',
    component: EditComponent
  },
  {
    path: 'pending',
    component: PendingComponent
  },
  {
    path: 'posts/:id/reject',
    component: RejectComponent
  },
  {
    path: 'posts/rejected',
    component: RejectedComponent
  },
  {
    path: 'posts/:id',
    component: PostDetailComponent
  },
  {
    path: 'comment/:id/edit',
    component: CommentEditComponent
  },
  {
    path: 'notifications',
    component: NotificationComponent
  },
  {
    path: '',
    redirectTo: 'posts',
    pathMatch: 'full'
  },
];
