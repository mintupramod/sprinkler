classification tree purity vs accuracy graph:
> myData <- read.delim("output/krkpa7.tsv",header=T,sep="\t",dec=".");
> library(ggplot2);
> ggplot(myData, aes(x=Purity, y=Accuracy)) +geom_point(size=1.2) +geom_smooth()+theme_bw();
> ggsave(file="test.pdf");

ttt-regression graph:
> myData <- read.delim("output/ttt-regression.tsv",header=T,sep="\t",dec=".");
> library(ggplot2);
> myData$Degree <- as.factor(myData$Degree);
> ggplot(myData, aes(x=Degree, y=Accuracy)) +geom_bar(stat="identity", fill="grey") +theme_bw();
> ggsave(file="test.pdf");

ttt-run3 vs ttt-regression graph:
> myData <- read.delim("output/ttt-run3.tsv",header=T,sep="\t",dec=".");
> library(ggplot2);
> ggplot(myData, aes(x=Purity, y=Accuracy)) +geom_point(size=1.2) +geom_smooth() +geom_hline(yintercept=0.68189) +geom_text(aes(x,y, label="logistic regression, linear (0.682)"), data=data.frame(x=0, y=0.68189), size=4, hjust=0, vjust=-0.4) + geom_hline(yintercept=0.98534) +geom_text(aes(x,y, label="logistic regression, quadratic (0.985)"), data=data.frame(x=0, y=0.98534), size=4, hjust=0, vjust=1.2) + geom_hline(yintercept=0.991064) +geom_text(aes(x,y, label="logistic regression, quartic (0.991)"), data=data.frame(x=0, y=0.991064), size=4, hjust=-1.2, vjust=-0.4) +theme_bw();
> ggsave(file="test.pdf");

