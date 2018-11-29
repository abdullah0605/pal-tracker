package io.pivotal.pal.tracker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private Map db = new HashMap();
    long timeEntryId = 1;

    public TimeEntry create(TimeEntry timeEntry)
    {


        timeEntry.setId(timeEntryId);
        db.put(timeEntryId, timeEntry);
        timeEntryId = timeEntryId + 1;
               return timeEntry;

    }



    public TimeEntry find(long id)   {


        return (TimeEntry)db.get(id);

    }

    public TimeEntry delete(long id)   {
        db.remove(id);
        return (TimeEntry)db.get(id); }

    public List<TimeEntry> list() {return new ArrayList(db.values());}

    public TimeEntry update(long id, TimeEntry timeEntry) {
        timeEntry.setId(id);
         db.put(id,timeEntry);
        return((TimeEntry)db.get(id));
        }


}


