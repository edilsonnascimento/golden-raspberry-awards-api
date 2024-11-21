package org.enascimento.api.util;

import com.opencsv.bean.*;
import org.slf4j.*;

import java.io.Reader;
import java.nio.file.*;
import java.util.List;

public class CsvReader<T> implements FileReader<T> {

    private static final Logger LOG = LoggerFactory.getLogger(CsvReader.class);

    final Class<T> typeParameterClass;

    public CsvReader(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    @Override
    public List<T> read(String path) {
        try (Reader reader = Files.newBufferedReader(Paths.get(path))) {
            return read(reader);
        } catch (Exception e) {
            LOG.error("{}", e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<T> read(Reader reader) {
        try {
            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
                    .withType(typeParameterClass)
                    .withSeparator(';')
                    .build();

            return csvToBean.parse();
        } catch (Exception e) {
            LOG.error("{}", e.getMessage(), e);
        }
        return null;
    }
}