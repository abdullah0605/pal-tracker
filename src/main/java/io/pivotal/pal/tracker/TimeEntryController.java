package io.pivotal.pal.tracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    private  final TimeEntryRepository timeEntriesRepo;
    private  final CounterService counter ;
    private  final GaugeService gauge ;

    public TimeEntryController(
            TimeEntryRepository timeEntriesRepo,
            CounterService counter,
            GaugeService gauge
    ) {
        this.timeEntriesRepo = timeEntriesRepo;
        this.counter = counter;
        this.gauge = gauge;
    }


    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntry)
    {
        TimeEntry createdTimeEntry = timeEntriesRepo.create(timeEntry);
        counter.increment("TimeEntry.created");
        gauge.submit("timeEntries.count", timeEntriesRepo.list().size());

        return new ResponseEntity<>(createdTimeEntry, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable("id") long id)
    {
        TimeEntry timeEntry = timeEntriesRepo.find(id);
        if (timeEntry != null) {
            counter.increment("TimeEntry.read");
            return new ResponseEntity<>(timeEntry, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("{id}")
    public ResponseEntity<TimeEntry> update(@PathVariable("id")  long id, @RequestBody TimeEntry timeEntry){
        TimeEntry updatedTimeEntry = timeEntriesRepo.update(id, timeEntry);
        if (updatedTimeEntry != null) {
            counter.increment("TimeEntry.updated");
            return new ResponseEntity<>(updatedTimeEntry, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<TimeEntry>  delete(@PathVariable("id") long id) {
        timeEntriesRepo.delete(id);
        counter.increment("TimeEntry.deleted");
        gauge.submit("timeEntries.count", timeEntriesRepo.list().size());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    public ResponseEntity<List<TimeEntry>> list() {
        counter.increment("TimeEntry.listed");
        return new ResponseEntity<>(timeEntriesRepo.list(), HttpStatus.OK);
    }

}
