import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ReadFromS3Demo {

    private static void execute() throws IOException {
        final String BUCKET_NAME = "";
        final String OBJECT_NAME = "";

        final String ACCESS_KEY = "";
        final String SECRET_KEY = "";

        //1 - Setup S3 Client
        AWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_1)
                .build();

        //2 - Get Object from S3 - Option 1 - Write to Disk
        GetObjectRequest request = new GetObjectRequest(BUCKET_NAME, OBJECT_NAME);
        File newFile = new File("YOUR_FILE_PATH_NAME_HERE!!!!");

        s3Client.getObject(request, newFile);

        //3 - Get Object from S3 - Option 2 - Process in memory
        S3Object s3Object = s3Client.getObject(BUCKET_NAME, OBJECT_NAME);

        InputStream inputStream = s3Object.getObjectContent();

        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = CsvSchema.emptySchema().withHeader();

        MappingIterator<Person> readValues = csvMapper.readerWithTypedSchemaFor(Person.class)
                .with(schema)
                .readValues(inputStream.readAllBytes());

        while (readValues.hasNext()) {
            Person current = readValues.next();
            System.out.println(current);
        }


    }


    public static void main(String[] args) throws IOException {
        execute();

    }
}
