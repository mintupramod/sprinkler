function out = mapFeatureRic(x, d)
% MAPFEATURE Feature mapping function to polynomial features
%
%   recursive part of mapFeature
%
if (length(x)==1)
  out=[];
  for i=0:d
    out=[out x(1)^i];
  end
elseif (d==0)
  out=1;
else
  out=[];
  for i=0:d
    temp=x(1)^i * mapFeatureRic(x(2:end), d-i);
    out=[out temp];
  end

end
