package io.pivotal.pal.tracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TimeEntryController {

    TimeEntryRepository repository;

    public TimeEntryController() {}

    @Autowired
    public TimeEntryController(TimeEntryRepository repository) {
        this.repository = repository;

    }

    @PostMapping("/time-entries")
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntry)
    {
        return new ResponseEntity<TimeEntry>(repository.create(timeEntry), HttpStatus.CREATED);
    }

    @GetMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable("id") long timeEntryId)
    {
        TimeEntry find = repository.find(timeEntryId);
        if (find == null)
            return new ResponseEntity<TimeEntry>(find, HttpStatus.NOT_FOUND);
            else return new ResponseEntity<TimeEntry>(find, HttpStatus.OK);

    }

    @PutMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> update(@PathVariable("id")  long timeEntryId, @RequestBody TimeEntry expected){
        TimeEntry updated = repository.update(timeEntryId,expected);
        if (updated == null) return new ResponseEntity<TimeEntry>( updated, HttpStatus.NOT_FOUND);
        else return new ResponseEntity<TimeEntry>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry>  delete(@PathVariable("id") long timeEntryId) {
        return new ResponseEntity<TimeEntry>(repository.delete(timeEntryId), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {
        return new ResponseEntity<List<TimeEntry>>((repository.list()), HttpStatus.OK);
    }
}
