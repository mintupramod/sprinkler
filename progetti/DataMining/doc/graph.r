classification tree purity vs accuracy graph:
> myData <- read.delim("krkpa7.tsv",header=T,sep="\t",dec=".");
> library(ggplot2);
> ggplot(myData, aes(x=Purity, y=Accuracy)) + geom_point(size=1.2) + geom_smooth()+theme_bw();
> ggsave(file="test.pdf");


ttt-regression graph:
> myData <- read.delim("data/ttt-regression.tsv",header=T,sep="\t",dec=".");
> myData$Degree <- as.factor(myData$Degree);
> ggplot(myData, aes(x=Degree, y=Accuracy)) + geom_bar(stat="identity", fill="grey") +theme_bw();
> ggsave(file="test.pdf");
