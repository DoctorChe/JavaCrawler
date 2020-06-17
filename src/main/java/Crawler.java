import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Crawler {

    public static void main(String[] args) throws IOException {
//        Parser.run();

//        news = crawler.db.get_news(crawler.id1)
//        logger.info(f'Новость из БД с id={crawler.id1}:\n{news}')
//        s = crawler.db.search('author', crawler.author)
//        for hit in s['hits']:
//        logger.info('%(author)s | %(title)s | %(url)s | %(pub_date)s' % hit['_source'])
//        logger.info(crawler.db.aggregate('author'))

//        analyzer = Analyzer()
//        words1 = crawler.db.get_words(crawler.id1)
//        words2 = crawler.db.get_words(crawler.id2)
//        logger.info(f'Shingles+MinHash: {analyzer.shingles_min_hash(words1, words2)}')

//        List<String> words1 = Elastic.getWords();
//        List<String> words2 = Elastic.getWords();
//        double smh = Analyzer.shinglesMinHash(words1, words2);
//        System.out.format("Shingles+MinHash: %f", smh);
        List<String> words1 = new ArrayList<>();
        List<String> words2 = new ArrayList<>();
        words1.add("abcde");
        words1.add("fghij");
        words1.add("klmnopq");
        words1.add("rst");
        words1.add("uvw");
        words1.add("xyz");
        words2.add("abcde");
        words2.add("fghij");
        words2.add("klmnopq");
        words2.add("xyz");
        words2.add("eeeee");
        words2.add("ffff");
        System.out.format("Shingles: %s", Analyzer.shinglesMinHash(words1, words2));
    }
}
