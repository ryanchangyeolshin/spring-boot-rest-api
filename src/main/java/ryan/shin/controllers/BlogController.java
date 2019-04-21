package ryan.shin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ryan.shin.items.Blog;
import ryan.shin.repositories.BlogRepository;
import java.util.Map;
import java.util.List;

@RestController
public class BlogController {
    @Autowired
    BlogRepository blogRespository;

    @GetMapping("/blog")
    public List<Blog> index() {
        return blogRespository.findAll();
    }

    @GetMapping("/blog/{id}")
    public Blog show(@PathVariable String id) {
        int blogId = Integer.parseInt(id);
        return blogRespository.findOne(blogId);
    }

    @PostMapping("/blog/search")
    public List<Blog> search(@RequestBody Map<String, String> body) {
        String searchTerm = body.get("text");
        return blogRespository.findByTitleContainingOrContentContaining(searchTerm, searchTerm);
    }

    @PostMapping("/blog")
    public Blog create(@RequestBody Map<String, String> body) {
        String title = body.get("title");
        String content = body.get("content");
        Blog newBlog = new Blog(title, content);
        return blogRespository.save(newBlog);
    }

    @PutMapping("/blog/{id}")
    public Blog update(@PathVariable String id, @RequestBody Map<String, String> body) {
        int blogId = Integer.parseInt(id);
        Blog blog = blogRespository.findOne(blogId);
        blog.setTitle(body.get("title"));
        blog.setContent(body.get("content"));
        return blogRespository.save(blog);
    }

    @DeleteMapping("/blog/{id}")
    public String delete(@PathVariable String id) {
        int blogId = Integer.parseInt(id);
        blogRespository.delete(blogId);
        return String.format("Blog with %s has been deleted", id);
    }
}