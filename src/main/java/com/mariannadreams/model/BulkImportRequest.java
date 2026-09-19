package com.mariannadreams.model;

import lombok.Data;
import java.util.List;

@Data
public class BulkImportRequest {
    private Long songId;
    private List<Lyric> blocks;
}
