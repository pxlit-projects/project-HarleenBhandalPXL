export class Comment {
  id?: number;
  postId: number;
  content: string | null | undefined;
  userName: string | null | undefined;

  constructor(id: number, postId: number, content: string, username: string){
    this.id = id;
    this.postId = postId;
    this.content = content;
    this.userName = username;
  }
}
