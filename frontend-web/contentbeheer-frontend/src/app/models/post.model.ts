export class Post {
  id?: number;
  title: string;
  content: string;
  author: string;
  creationDate?: string;

  constructor(id: number, title: string, content: string, author: string, creationDate: string){
    this.id = id;
    this.title = title;
    this.content = content;
    this.author = author;
    this.creationDate = creationDate;
  }
}
