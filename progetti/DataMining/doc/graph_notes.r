


> myData <- read.delim("kr",header=T,sep="\t",dec=".");
> library(ggplot2);
> ggplot(myData, aes(x=Purity, y=Accuracy)) + geom_point(size=1.2) + geom_smooth()+theme_bw();
> ggsave(file="test.pdf");
Saving 9.67" x 8.16" image
> 



 + scale_x_reverse() reverse x scale
