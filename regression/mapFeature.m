function out = mapFeature(X, d)
% MAPFEATURE Feature mapping function to polynomial features
%
%   X feature matrix
%   d max degree
%
%   Returns a new feature matrix with more features, comprising of 
%   X1, X2, X1.^2, X2.^2, X1*X2, X1*X2.^2, etc..
%

out=[];

for i=1:size(X,1)
  out(end+1,:)=mapFeatureRic(X(i,:), d);
end

end
