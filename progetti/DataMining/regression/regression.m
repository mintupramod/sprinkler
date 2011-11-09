#! /usr/bin/octave -qf

arg_list = argv();
data_file = arg_list{[1]};

samples = 10;
degree = 6;

% Set regularization parameter lambda (you should vary this)
lambda = 0.1;

if strcmp(data_file, "-qf")==1,
  disp('A Logistic Regression classificator in Octave.');
  disp('use: ./regression.m dataset');
  disp('     where dataset is the dataset file');
  disp('ex.  ./regression.m ttt.data');
  quit;
end



fprintf('Estimating training accuracy with .632 bootstrap validator with %d samples\n', samples);

%% Initialization
%clear ; close all; clc

%% Load Data
%  The first columns contains the features, the last column contains the label.
data = load(data_file);
X = data(:, [1:end-1]); y = data(:, end);

% Add intercept term to x and X_test (cancel if use mapFeatures)
m = size(X, 1);
%X = [ones(m, 1) X];

%% =========== Part 1: Regularized Logistic Regression ============
%  In this part, you are given a dataset with data points that are not
%  linearly separable. However, you would still like to use logistic 
%  regression to classify the data points. 
%
%  To do so, you introduce more features to use -- in particular, you add
%  polynomial features to our data matrix (similar to polynomial
%  regression).
%

% Add Polynomial Features
% Note that mapFeature also adds a column of ones for us, so the intercept
% term is handled
X = mapFeature(X, degree);
%X = [ones(m, 1) X];
data =[X y];








% Initialize fitting parameters
initial_theta = zeros(size(X, 2), 1);



% Set Options
options = optimset('GradObj', 'on', 'MaxIter', 400);

% Optimize
[theta, J, exit_flag] = ...
	fminunc(@(t)(costFunctionReg(t, X, y, lambda)), initial_theta, options);
	
% Compute accuracy on the training set
p = predict(theta, X);
accS = mean(double(p == y));



% .632 bootstrap
accBoot = 0;

for i = 1:samples

  % generate a new bootstrap
  ix=ceil(unifrnd(0, m, m, 1));   % indexes

  B=data(ix,:);      % bootstrap sample

  T=data;            % Testing set
  T(ix,:)=[];
  yt= T(:, end);
  T = T(:,1:end-1);
  
  Xi = B(:, [1:end-1]); yi = B(:, end);
  %Xi = [ones(m, 1) Xi];
  
  % Optimize and compute accuracy on testing set
  initial_theta = zeros(size(Xi, 2), 1);
  [theta, J, exit_flag] = ...
  	fminunc(@(t)(costFunctionReg(t, Xi, yi, lambda)), initial_theta, options);

  pi = predict(theta, T);
  acc = mean(double(pi == yt));

  accBoot = accBoot + (.632 * acc + .368 * accS) / samples;

end

fprintf('Train Accuracy: %f\n', accBoot);


